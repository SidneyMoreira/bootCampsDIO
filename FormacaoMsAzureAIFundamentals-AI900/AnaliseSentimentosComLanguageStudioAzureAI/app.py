import argparse
import os
import re
from typing import List, Dict, Any

try:
    # Optional: load .env if present
    from dotenv import load_dotenv  # type: ignore

    load_dotenv()
except Exception:
    pass

from azure.ai.textanalytics import TextAnalyticsClient
from azure.core.credentials import AzureKeyCredential


def read_quoted_sentences(path: str) -> List[str]:
    # Tenta UTF-8 e faz fallback para CP1252 (Windows) se necessário
    try:
        with open(path, "r", encoding="utf-8") as f:
            content = f.read()
    except UnicodeDecodeError:
        with open(path, "r", encoding="cp1252") as f:
            content = f.read()

    # Captura somente o texto entre aspas duplas
    # Suporta múltiplas ocorrências por linha
    sentences = re.findall(r'"([^"\n]+)"', content)
    # Limpa espaços em branco e remove vazios
    sentences = [s.strip() for s in sentences if s.strip()]
    return sentences


def get_client() -> TextAnalyticsClient:
    # Tenta variáveis com nomes comuns
    endpoint = (
        os.getenv("AZURE_LANGUAGE_ENDPOINT")
        or os.getenv("AZURE_AI_ENDPOINT")
        or os.getenv("AZURE_AI_SERVICE_ENDPOINT")
    )
    key = (
        os.getenv("AZURE_LANGUAGE_KEY")
        or os.getenv("AZURE_AI_KEY")
        or os.getenv("AZURE_AI_SERVICE_KEY")
    )

    if not endpoint or not key:
        raise RuntimeError(
            "Defina as variáveis de ambiente para o serviço Azure AI Language: "
            "AZURE_LANGUAGE_ENDPOINT e AZURE_LANGUAGE_KEY (ou equivalentes)."
        )

    return TextAnalyticsClient(endpoint=endpoint, credential=AzureKeyCredential(key))


def analyze_sentiments(client: TextAnalyticsClient, docs: List[str], language: str = "pt") -> List[Dict[str, Any]]:
    resultados: List[Dict[str, Any]] = []
    # A API aceita lotes; um tamanho seguro é 10
    batch_size = 10
    for i in range(0, len(docs), batch_size):
        chunk = docs[i : i + batch_size]
        results = client.analyze_sentiment(chunk, language=language)
        for doc, res in zip(chunk, results):
            if res.is_error:  # type: ignore[attr-defined]
                print(f"[ERRO] {res.error.code}: {res.error.message}")  # type: ignore[attr-defined]
                resultados.append(
                    {
                        "texto": doc,
                        "sentimento": "erro",
                        "positivo": None,
                        "neutro": None,
                        "negativo": None,
                        "erro": f"{res.error.code}: {res.error.message}",  # type: ignore[attr-defined]
                    }
                )
                continue

            # Resumo por documento (console)
            print("-" * 80)
            print(f"Texto: {doc}")
            print(
                "Sentimento:",
                res.sentiment,
                f"(pos={res.confidence_scores.positive:.2f}",
                f"neu={res.confidence_scores.neutral:.2f}",
                f"neg={res.confidence_scores.negative:.2f})",
            )

            resultados.append(
                {
                    "texto": doc,
                    "sentimento": str(res.sentiment),
                    "positivo": float(res.confidence_scores.positive),
                    "neutro": float(res.confidence_scores.neutral),
                    "negativo": float(res.confidence_scores.negative),
                    "erro": None,
                }
            )

    return resultados


def _md_escape(text: str) -> str:
    # Escapa pipes e substitui quebras de linha por espaço
    return text.replace("|", "\\|").replace("\n", " ")


def write_markdown_table(resultados: List[Dict[str, Any]], output_path: str) -> None:
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    lines: List[str] = []
    lines.append("| # | Texto | Sentimento | Positivo | Neutro | Negativo |")
    lines.append("|---:|---|---|---:|---:|---:|")
    for idx, r in enumerate(resultados, start=1):
        texto = _md_escape(r["texto"]) if r.get("texto") else ""
        sent = r.get("sentimento") or ""
        pos = r.get("positivo")
        neu = r.get("neutro")
        neg = r.get("negativo")
        if pos is None and neu is None and neg is None and r.get("erro"):
            # Marca erro no sentimento e deixa scores vazios
            sent = f"erro: {r['erro']}"
            pos_s = neu_s = neg_s = ""
        else:
            pos_s = f"{pos:.2f}" if isinstance(pos, float) else ""
            neu_s = f"{neu:.2f}" if isinstance(neu, float) else ""
            neg_s = f"{neg:.2f}" if isinstance(neg, float) else ""

        lines.append(f"| {idx} | {texto} | {sent} | {pos_s} | {neu_s} | {neg_s} |")

    with open(output_path, "w", encoding="utf-8") as f:
        f.write("\n".join(lines) + "\n")


def main():
    parser = argparse.ArgumentParser(
        description=(
            "Analisa sentimentos com Azure AI Language (AI Foundry/Language Studio). "
            "Lê apenas o texto entre aspas em um arquivo."
        )
    )
    parser.add_argument(
        "--file",
        "-f",
        default=os.path.join("inputs", "sentences.txt"),
        help="Caminho do arquivo de entrada (default: inputs/sentences.txt)",
    )
    parser.add_argument(
        "--lang",
        "-l",
        default="pt",
        help="Código de idioma (ex.: pt, en). Default: pt",
    )
    parser.add_argument(
        "--out",
        "-o",
        default=os.path.join("outputs", "sentiment_results.md"),
        help="Caminho do arquivo Markdown de saída (default: outputs/sentiment_results.md)",
    )
    args = parser.parse_args()

    sentences = read_quoted_sentences(args.file)
    if not sentences:
        print("Nenhum texto entre aspas encontrado no arquivo.")
        return

    client = get_client()
    resultados = analyze_sentiments(client, sentences, language=args.lang)
    write_markdown_table(resultados, args.out)
    print(f"\nArquivo Markdown gerado em: {args.out}")


if __name__ == "__main__":
    main()
