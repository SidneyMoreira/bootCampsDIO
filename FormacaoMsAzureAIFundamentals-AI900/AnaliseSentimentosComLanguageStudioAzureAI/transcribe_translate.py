import argparse
import json
import os
import threading
import glob
from typing import List, Dict, Any, Optional

try:
    from dotenv import load_dotenv  # type: ignore

    # Garante que os valores do .env sobreponham variáveis do sistema
    load_dotenv(override=True)
except Exception:
    pass

import azure.cognitiveservices.speech as speechsdk
import requests


def _getenv(name: str) -> Optional[str]:
    v = os.getenv(name)
    if v is None:
        return None
    v = v.strip()
    return v or None


def _md_escape(text: str) -> str:
    return (text or "").replace("|", "\\|").replace("\n", " ")


def list_audio_files(folder: str) -> List[str]:
    exts = {".wav", ".mp3", ".ogg", ".m4a", ".flac", ".wma", ".mp4"}
    files: List[str] = []
    for name in sorted(os.listdir(folder)):
        path = os.path.join(folder, name)
        if os.path.isfile(path) and os.path.splitext(name.lower())[1] in exts:
            files.append(path)
    return files


def expand_files(patterns: List[str]) -> List[str]:
    exts = {".wav", ".mp3", ".ogg", ".m4a", ".flac", ".wma", ".mp4"}
    out: List[str] = []
    for pat in patterns:
        # Expande glob e também aceita caminho direto
        matches = glob.glob(pat)
        if not matches and os.path.exists(pat):
            matches = [pat]
        for m in matches:
            if os.path.isfile(m) and os.path.splitext(m.lower())[1] in exts:
                out.append(m)
    # Ordena para estabilidade
    return sorted(set(out))


def get_speech_config() -> speechsdk.SpeechConfig:
    key = (
        _getenv("AZURE_SPEECH_KEY")
        or _getenv("SPEECH_KEY")
        or _getenv("AZURE_AI_SPEECH_KEY")
    )
    region = (
        _getenv("AZURE_SPEECH_REGION")
        or _getenv("SPEECH_REGION")
        or _getenv("AZURE_AI_SPEECH_REGION")
    )
    endpoint = _getenv("AZURE_SPEECH_ENDPOINT")

    if endpoint:
        # Se for endpoint multi-serviços (cognitiveservices.azure.com), o Speech SDK não aceita.
        # Nesses casos, faça fallback para chave+região, que é o modo recomendado.
        if "cognitiveservices.azure.com" in endpoint:
            if not key or not region:
                raise RuntimeError(
                    "AZURE_SPEECH_ENDPOINT aponta para Cognitive Services. "
                    "Para Speech SDK, use AZURE_SPEECH_KEY e AZURE_SPEECH_REGION (ex.: eastus, brazilsouth)."
                )
            speech_config = speechsdk.SpeechConfig(subscription=key, region=region)
        else:
            if not key:
                raise RuntimeError("Defina a chave do Speech em AZURE_SPEECH_KEY/SPEECH_KEY.")
            speech_config = speechsdk.SpeechConfig(subscription=key, endpoint=endpoint)
    else:
        if not key or not region:
            raise RuntimeError(
                "Defina AZURE_SPEECH_KEY e AZURE_SPEECH_REGION (ou equivalentes)."
            )
        speech_config = speechsdk.SpeechConfig(subscription=key, region=region)

    # Formato detalhado pode incluir confidences em alguns modos; mantemos simples
    return speech_config


def transcribe_file(
    file_path: str,
    speech_config: speechsdk.SpeechConfig,
    speech_language: Optional[str] = None,
    auto_detect_languages: Optional[List[str]] = None,
) -> Dict[str, Any]:
    audio_config = speechsdk.AudioConfig(filename=file_path)

    try:
        if speech_language:
            recognizer = speechsdk.SpeechRecognizer(
                speech_config=speech_config,
                language=speech_language,
                audio_config=audio_config,
            )
        else:
            # Auto-detect entre os idiomas fornecidos
            langs = auto_detect_languages or ["pt-BR", "en-US", "es-ES"]
            auto_cfg = speechsdk.languageconfig.AutoDetectSourceLanguageConfig(langs)
            recognizer = speechsdk.SpeechRecognizer(
                speech_config=speech_config,
                auto_detect_source_language_config=auto_cfg,
                audio_config=audio_config,
            )
    except Exception as e:
        return {
            "arquivo": os.path.basename(file_path),
            "texto": "",
            "idioma_detectado": None,
            "erro": f"Falha ao criar SpeechRecognizer: {e}. Verifique AZURE_SPEECH_KEY/REGION e o formato do arquivo.",
        }

    all_text: List[str] = []
    detected_lang: Optional[str] = None
    done = threading.Event()
    error_msg: Optional[str] = None

    # recognized callback defined below (recognized_cb)

    def recognized_cb(evt):
        nonlocal detected_lang
        try:
            result = evt.result
            if result and result.text:
                all_text.append(result.text)
            if hasattr(result, "properties"):
                lang = result.properties.get(
                    speechsdk.PropertyId.SpeechServiceConnection_AutoDetectSourceLanguageResult
                )
                if lang:
                    detected_lang = str(lang)
        except Exception:
            pass

    def canceled_cb(evt):
        nonlocal error_msg
        # Alguns formatos/arquivos disparam 'canceled' ao fim do stream.
        # Só marcamos erro se não houver texto reconhecido.
        try:
            # Tenta extrair detalhes de forma defensiva (event ou result)
            details = None
            for attr_chain in [
                "result.cancellation_details",
                "cancellation_details",
            ]:
                obj = evt
                for part in attr_chain.split("."):
                    obj = getattr(obj, part, None)
                if obj is not None:
                    details = obj
                    break

            if details is not None:
                reason = getattr(details, "reason", None)
                code = getattr(details, "error_code", None)
                extra = getattr(details, "error_details", None)
                if not all_text:
                    error_msg = f"{reason}: {code} {extra}".strip()
            else:
                if not all_text:
                    error_msg = "Reconhecimento cancelado"
        except Exception:
            if not all_text:
                error_msg = "Reconhecimento cancelado"
        finally:
            done.set()

    def stopped_cb(evt):
        done.set()

    recognizer.recognized.connect(recognized_cb)
    recognizer.canceled.connect(canceled_cb)
    recognizer.session_stopped.connect(stopped_cb)

    try:
        recognizer.start_continuous_recognition_async().get()
        done.wait()
    finally:
        try:
            recognizer.stop_continuous_recognition_async().get()
        except Exception:
            pass

    text = " ".join(all_text).strip()
    # Se obteve texto, não tratamos cancelamento final como erro
    if text and error_msg:
        error_msg = None
    return {
        "arquivo": os.path.basename(file_path),
        "texto": text,
        "idioma_detectado": detected_lang,
        "erro": error_msg,
    }


def get_translator_cfg():
    key = (
        _getenv("AZURE_TRANSLATOR_KEY")
        or _getenv("TRANSLATOR_TEXT_KEY")
        or _getenv("COGNITIVE_TRANSLATOR_KEY")
    )
    endpoint = (
        _getenv("AZURE_TRANSLATOR_ENDPOINT")
        or _getenv("TRANSLATOR_TEXT_ENDPOINT")
        or "https://api.cognitive.microsofttranslator.com"
    )
    region = (
        _getenv("AZURE_TRANSLATOR_REGION")
        or _getenv("TRANSLATOR_TEXT_REGION")
        or _getenv("COGNITIVE_REGION")
    )
    if not key:
        raise RuntimeError("Defina AZURE_TRANSLATOR_KEY (ou equivalente) para tradução.")
    return endpoint.rstrip("/"), key, region


def translate_text(text: str, to_lang: str, from_lang: Optional[str] = None) -> str:
    if not text:
        return ""
    endpoint, key, region = get_translator_cfg()
    url = f"{endpoint}/translate"
    params = {"api-version": "3.0", "to": to_lang}
    if from_lang:
        params["from"] = from_lang
    headers = {
        "Ocp-Apim-Subscription-Key": key,
        "Content-Type": "application/json",
    }
    if region:
        headers["Ocp-Apim-Subscription-Region"] = region
    resp = requests.post(url, params=params, headers=headers, json=[{"Text": text}], timeout=60)
    resp.raise_for_status()
    data = resp.json()
    try:
        return data[0]["translations"][0]["text"]
    except Exception:
        return json.dumps(data, ensure_ascii=False)


def write_markdown_table(rows: List[Dict[str, Any]], output_path: str, to_lang: str) -> None:
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    lines: List[str] = []
    lines.append(f"| # | Arquivo | Idioma | Texto | Tradução ({to_lang}) | Erro |")
    lines.append("|---:|---|---|---|---|---|")
    for idx, r in enumerate(rows, start=1):
        lines.append(
            "| {} | {} | {} | {} | {} | {} |".format(
                idx,
                _md_escape(r.get("arquivo", "")),
                _md_escape(r.get("idioma_detectado") or ""),
                _md_escape(r.get("texto") or ""),
                _md_escape(r.get("traducao") or ""),
                _md_escape(r.get("erro") or ""),
            )
        )
    with open(output_path, "w", encoding="utf-8") as f:
        f.write("\n".join(lines) + "\n")


def main():
    parser = argparse.ArgumentParser(
        description=(
            "Transcreve arquivos de áudio da pasta de entrada e traduz o texto para o idioma desejado, salvando uma tabela Markdown na pasta de saída."
        )
    )
    parser.add_argument(
        "--inputs",
        "-i",
        default=os.path.join("inputs"),
        help="Pasta com arquivos de áudio (default: inputs)",
    )
    parser.add_argument(
        "--files",
        "-f",
        nargs="+",
        help="Um ou mais arquivos de áudio ou padrões glob (ex.: inputs/*.wav)",
    )
    parser.add_argument(
        "--out",
        "-o",
        default=os.path.join("outputs", "transcriptions.md"),
        help="Arquivo Markdown de saída (default: outputs/transcriptions.md)",
    )
    parser.add_argument(
        "--to",
        "-t",
        default="en",
        help="Idioma alvo da tradução (ex.: en, pt, es). Default: en",
    )
    parser.add_argument(
        "--speech-lang",
        default=None,
        help="Idioma do áudio para reconhecimento (ex.: pt-BR). Se omitido, tenta auto-detecção.",
    )
    parser.add_argument(
        "--detect",
        default="pt-BR,en-US,es-ES",
        help="Lista de idiomas para auto-detecção (se --speech-lang não for usado)",
    )
    args = parser.parse_args()

    audio_files: List[str] = []
    if args.files:
        audio_files = expand_files(args.files)
        if not audio_files:
            raise SystemExit("Nenhum arquivo correspondente encontrado para --files.")
    else:
        if not os.path.isdir(args.inputs):
            raise SystemExit(f"Pasta de entrada não encontrada: {args.inputs}")
        audio_files = list_audio_files(args.inputs)
        if not audio_files:
            raise SystemExit("Nenhum arquivo de áudio encontrado na pasta de entrada.")

    speech_config = get_speech_config()
    rows: List[Dict[str, Any]] = []
    for path in audio_files:
        res = transcribe_file(
            path,
            speech_config,
            speech_language=args.speech_lang,
            auto_detect_languages=[s.strip() for s in args.detect.split(",") if s.strip()],
        )
        texto = res.get("texto") or ""
        try:
            traducao = translate_text(texto, args.to, None)
        except Exception as e:
            traducao = ""
            res["erro"] = (res.get("erro") or "") + f" | Traducao: {e}"
        res["traducao"] = traducao
        rows.append(res)

    write_markdown_table(rows, args.out, args.to)
    print(f"Tabela Markdown gerada em: {args.out}")


if __name__ == "__main__":
    main()
