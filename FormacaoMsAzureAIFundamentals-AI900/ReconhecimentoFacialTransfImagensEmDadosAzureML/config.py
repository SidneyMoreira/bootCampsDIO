"""
Carregamento simples de variáveis a partir de um arquivo .env.

Uso:
  from config import load_env
  load_env()  # carrega valores de .env para os.environ (se existir)

O formato do .env é KEY=VALUE por linha, com linhas em branco e comentários (#) ignorados.
"""

from __future__ import annotations

import os
from pathlib import Path
from typing import Iterable, Tuple


def _parse_line(line: str) -> Tuple[str, str] | None:
    s = line.strip()
    if not s or s.startswith("#"):
        return None
    # Suporta KEY=VALUE (primeiro '=')
    if "=" not in s:
        return None
    key, value = s.split("=", 1)
    return key.strip(), value.strip().strip('"').strip("'")


def load_env(dotenv_path: str | os.PathLike[str] = ".env", override: bool = False) -> None:
    path = Path(dotenv_path)
    if not path.exists():
        return
    try:
        for line in path.read_text(encoding="utf-8").splitlines():
            parsed = _parse_line(line)
            if not parsed:
                continue
            key, value = parsed
            if override or key not in os.environ:
                os.environ[key] = value
    except Exception:
        # Evita quebrar a execução caso o .env tenha problemas
        pass

