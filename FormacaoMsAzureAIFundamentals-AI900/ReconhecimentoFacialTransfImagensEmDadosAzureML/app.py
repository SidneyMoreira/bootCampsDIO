
"""
app2.py — Detecção de objetos (Image Analysis v4)

Melhorias:
- CLI com suporte a URL, arquivo único (`--image-path`) ou múltiplos arquivos em `--inputs-dir`.
- Fallback para uma URL padrão quando nada é informado.
- Leitura de credenciais via variáveis de ambiente `VISION_ENDPOINT` e `VISION_KEY`.
- Saída legível e opção de salvar JSON.
"""

from __future__ import annotations

import os
import sys
import json
import argparse
from pathlib import Path
from typing import Any, Dict, Iterable, List

import requests


DEFAULT_SAMPLE_URL = (
    "https://ai.azure.com/common/vision/objectDetection/ObjectDetectionSample1.jpg"
)
API_VERSION = "2023-10-01"

# Carrega variáveis de um arquivo .env (se existir)
try:
    from config import load_env

    load_env()
except Exception:
    pass


def _require_env(name: str) -> str:
    v = os.environ.get(name)
    if not v:
        raise RuntimeError(
            f"Variável de ambiente '{name}' não definida. Configure VISION_ENDPOINT e VISION_KEY."
        )
    return v


def _build_endpoint_url() -> str:
    endpoint = _require_env("VISION_ENDPOINT").rstrip("/")
    return f"{endpoint}/computervision/imageanalysis:analyze?features=objects&api-version={API_VERSION}"


def detect_objects_from_url(image_url: str) -> Dict[str, Any]:
    url = _build_endpoint_url()
    key = _require_env("VISION_KEY")
    headers = {
        "Ocp-Apim-Subscription-Key": key,
        "Content-Type": "application/json; charset=utf-8",
    }
    # Enviamos ambos campos para maior compatibilidade
    payload = {"url": image_url, "uri": image_url}
    resp = requests.post(url, headers=headers, json=payload, timeout=60)
    resp.raise_for_status()
    return resp.json()


def detect_objects_from_file(image_path: Path) -> Dict[str, Any]:
    url = _build_endpoint_url()
    key = _require_env("VISION_KEY")
    headers = {
        "Ocp-Apim-Subscription-Key": key,
        "Content-Type": "application/octet-stream",
    }
    with image_path.open("rb") as f:
        data = f.read()
    resp = requests.post(url, headers=headers, data=data, timeout=60)
    resp.raise_for_status()
    return resp.json()


def summarize_objects(result: Dict[str, Any]) -> Dict[str, Any]:
    meta = result.get("metadata") or {}
    obj_res = result.get("objectsResult") or {}
    values = obj_res.get("values") or []
    items: List[Dict[str, Any]] = []
    for v in values:
        bbox = v.get("boundingBox") or {}
        tags = v.get("tags") or []
        top_tag = None
        if tags:
            best = max(tags, key=lambda t: t.get("confidence", 0.0))
            top_tag = {
                "name": best.get("name"),
                "confidence": best.get("confidence"),
            }
        items.append(
            {
                "bbox": {
                    "x": bbox.get("x"),
                    "y": bbox.get("y"),
                    "w": bbox.get("w"),
                    "h": bbox.get("h"),
                },
                "top_tag": top_tag,
            }
        )
    return {
        "modelVersion": result.get("modelVersion"),
        "image": {"width": meta.get("width"), "height": meta.get("height")},
        "count": len(items),
        "objects": items,
        "raw": result,
    }


def print_summary(summary: Dict[str, Any], source: str) -> None:
    print(f"Objetos detectados em: {source}")
    w = summary.get("image", {}).get("width")
    h = summary.get("image", {}).get("height")
    print(f"Dimensões: {w}x{h}  |  Total: {summary['count']}")
    for i, item in enumerate(summary["objects"], start=1):
        b = item["bbox"]
        tt = item.get("top_tag") or {}
        print(
            f"- #{i}: bbox [x={b['x']}, y={b['y']}, w={b['w']}, h={b['h']}], tag: {tt.get('name')} ({tt.get('confidence')})"
        )


def iter_input_images(inputs_dir: Path) -> Iterable[Path]:
    exts = {".jpg", ".jpeg", ".png", ".bmp", ".gif"}
    if not inputs_dir.exists():
        return []
    for p in sorted(inputs_dir.iterdir()):
        if p.is_file() and p.suffix.lower() in exts:
            yield p


def parse_args(argv: List[str]) -> argparse.Namespace:
    p = argparse.ArgumentParser(
        description="Detecção de objetos (Image Analysis v4)",
        formatter_class=argparse.ArgumentDefaultsHelpFormatter,
    )
    p.add_argument("--image-url", default=None, help="URL da imagem para análise")
    p.add_argument("--image-path", default=None, help="Caminho de um arquivo local para análise")
    p.add_argument("--inputs-dir", default=None, help="Diretório com imagens locais (processa todas)")
    p.add_argument("--output", default=None, help="Arquivo de saída JSON (modo único)")
    p.add_argument("--outputs-dir", default=None, help="Diretório para salvar JSONs (modo múltiplo)")
    return p.parse_args(argv)


def ensure_dir(path: Path) -> None:
    path.mkdir(parents=True, exist_ok=True)


def main(argv: List[str]) -> int:
    args = parse_args(argv)

    # Modo múltiplo: processa todos arquivos de um diretório
    if args.inputs_dir:
        inputs_dir = Path(args.inputs_dir)
        images = list(iter_input_images(inputs_dir))
        if not images:
            print(f"Nenhuma imagem encontrada em '{inputs_dir}'.", file=sys.stderr)
            return 1
        out_dir = Path(args.outputs_dir or "outputs")
        ensure_dir(out_dir)
        for img in images:
            try:
                result = detect_objects_from_file(img)
                summary = summarize_objects(result)
                print_summary(summary, str(img))
                out_file = out_dir / f"{img.stem}.objects.json"
                with out_file.open("w", encoding="utf-8") as f:
                    json.dump(summary, f, ensure_ascii=False, indent=2)
                print(f"→ Salvo: {out_file}")
            except Exception as ex:
                print(f"Falha em '{img}': {ex}", file=sys.stderr)
        return 0

    # Modo único: arquivo local tem precedência sobre URL
    source_desc = None
    try:
        if args.image_path:
            img_path = Path(args.image_path)
            source_desc = str(img_path)
            result = detect_objects_from_file(img_path)
        else:
            image_url = args.image_url or DEFAULT_SAMPLE_URL
            source_desc = image_url
            result = detect_objects_from_url(image_url)
    except Exception as ex:
        print(f"Erro ao analisar a imagem: {ex}", file=sys.stderr)
        return 2

    summary = summarize_objects(result)
    print_summary(summary, source_desc)

    if args.output:
        out_path = Path(args.output)
        ensure_dir(out_path.parent)
        with out_path.open("w", encoding="utf-8") as f:
            json.dump(summary, f, ensure_ascii=False, indent=2)
        print(f"→ Salvo: {out_path}")

    return 0


if __name__ == "__main__":
    raise SystemExit(main(sys.argv[1:]))
