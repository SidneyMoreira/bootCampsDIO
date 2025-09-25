#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
prepare_btc_investing.py  (v2)

Atualizações:
- Detecta separador automaticamente (inclusive ponto e vírgula ';').
- Corrige parsing de 'Change %' quando vier como '-3,31' (vírgula decimal).
- Mantém compatibilidade com formato antigo 'Sep 25, 2025' e '09/25/2025'.

Uso:
python prepare_btc_investing.py \
  --input btc_investing.csv \
  --output-csv btc_clean.csv \
  --json-a payload_A.json \
  --json-b payload_B.json \
  --predict-date 2025-09-25
"""

import argparse
import json
import math
import sys
from typing import Tuple

import numpy as np
import pandas as pd


def parse_args():
    p = argparse.ArgumentParser(description="Limpar CSV do Investing.com e gerar JSONs para Azure ML")
    p.add_argument("--input", required=True, help="Caminho do CSV original (Investing.com)")
    p.add_argument("--output-csv", default="btc_clean.csv", help="CSV limpo de saída")
    p.add_argument("--json-a", default="payload_A.json", help="JSON de teste (modelo A - sem features de série temporal)")
    p.add_argument("--json-b", default="payload_B.json", help="JSON de teste (modelo B - com features)")
    p.add_argument("--predict-date", default=None, help="Data (YYYY-MM-DD) para gerar o payload. Default: última linha")
    return p.parse_args()


def _to_float(x):
    """Converte string numérica com vírgula de milhares para float."""
    if x is None or (isinstance(x, float) and math.isnan(x)):
        return np.nan
    s = str(x).strip()
    if s in {"", "-", "nan", "None"}:
        return np.nan
    s = s.replace(",", "")
    try:
        return float(s)
    except Exception:
        return np.nan


def parse_volume(s: str) -> float:
    """Converte '62.58K' -> 62580, '1.2M' -> 1200000, '-' -> NaN"""
    if s is None:
        return np.nan
    s = str(s).strip().replace(",", "")
    if s in {"", "-", "nan", "None"}:
        return np.nan
    mult = 1.0
    if s.endswith(("K", "k")):
        mult = 1_000.0
        s = s[:-1]
    elif s.endswith(("M", "m")):
        mult = 1_000_000.0
        s = s[:-1]
    elif s.endswith(("B", "b")):
        mult = 1_000_000_000.0
        s = s[:-1]
    try:
        return float(s) * mult
    except Exception:
        return np.nan


def parse_change_pct(s: str) -> float:
    """
    Converte strings como '-3.31%', '-3,31', '1,16', '0.6%' para decimal:
    -> -0.0331, -0.0331, 0.0116, 0.006
    """
    if s is None:
        return np.nan
    s = str(s).strip().replace("%", "").replace(" ", "")
    # Se houver vírgula e não houver ponto, interpretamos vírgula como decimal
    if "," in s and "." not in s:
        s = s.replace(",", ".")
    # Remover separadores de milhar residuais
    if s.count(".") > 1:
        # Ex.: '1.234.56' -> remover o primeiro '.'
        parts = s.split(".")
        s = parts[0] + "." + "".join(parts[1:])
    if s in {"", "-", "nan", "None"}:
        return np.nan
    try:
        return float(s) / 100.0
    except Exception:
        return np.nan


def _read_csv_auto(path: str) -> pd.DataFrame:
    """
    Tenta ler com detecção automática de separador; se falhar, tenta ';'.
    Também tenta utf-8/latin-1 conforme necessário.
    """
    encodings = ["utf-8-sig", "utf-8", "latin-1"]
    for enc in encodings:
        try:
            df = pd.read_csv(path, sep=None, engine="python", encoding=enc)
            # Se leu tudo em uma coluna, tentar com separador ';'
            if len(df.columns) == 1:
                df = pd.read_csv(path, sep=";", encoding=enc)
            return df
        except Exception:
            continue
    # Última tentativa: separador ';' latin-1
    return pd.read_csv(path, sep=";", encoding="latin-1")


def load_and_clean(path: str) -> pd.DataFrame:
    df = _read_csv_auto(path)
    # Padronizar nomes de colunas (alguns CSVs vem com espaços)
    df.columns = [c.strip() for c in df.columns]

    expected = {"Date", "Price", "Open", "High", "Low", "Vol.", "Change %"}
    if not expected.issubset(set(df.columns)):
        raise ValueError(f"Colunas ausentes no CSV: {expected - set(df.columns)}. Encontradas: {list(df.columns)}")

    # Converter data
    # Formatos possíveis: 'Sep 25, 2025' ou '09/25/2025' ou '25/09/2025'
    df["Date"] = pd.to_datetime(df["Date"], errors="coerce")
    if df["Date"].isna().any():
        df["Date"] = pd.to_datetime(df["Date"], errors="coerce", dayfirst=True)
    if df["Date"].isna().any():
        raise ValueError("Não foi possível converter algumas datas. Verifique o formato da coluna 'Date'.")

    # Converter números
    for col in ["Price", "Open", "High", "Low"]:
        df[col] = df[col].apply(_to_float)

    # Volume e Change %
    df["Volume"] = df["Vol."].apply(parse_volume)
    df["ChangePct"] = df["Change %"].apply(parse_change_pct)

    # Ordenar por data ascendente
    df = df.sort_values("Date").reset_index(drop=True)

    # Derivados de data
    df["day"] = df["Date"].dt.day.astype(int)
    df["month"] = df["Date"].dt.month.astype(int)
    df["year"] = df["Date"].dt.year.astype(int)
    # weekday: 0=segunda...6=domingo (padrão Python)
    df["weekday"] = df["Date"].dt.weekday.astype(int)

    return df


def add_time_features(df: pd.DataFrame) -> pd.DataFrame:
    out = df.copy()
    out["lag_1"] = out["Price"].shift(1)
    out["ma_7"] = out["Price"].rolling(7, min_periods=1).mean()
    out["ma_30"] = out["Price"].rolling(30, min_periods=1).mean()
    out["volatility_7"] = out["Price"].rolling(7, min_periods=2).std()
    return out


def build_payload_A(row: pd.Series) -> dict:
    cols = ["day", "month", "year", "weekday", "Open", "High", "Low", "Volume", "ChangePct"]
    data = [[
        int(row["day"]), int(row["month"]), int(row["year"]), int(row["weekday"]),
        float(row["Open"]), float(row["High"]), float(row["Low"]),
        float(row["Volume"]) if not pd.isna(row["Volume"]) else 0.0,
        float(row["ChangePct"]) if not pd.isna(row["ChangePct"]) else 0.0
    ]]
    return {"input_data": {"columns": cols, "index": [0], "data": data}}


def build_payload_B(row: pd.Series) -> dict:
    cols = [
        "day", "month", "year", "weekday",
        "Open", "High", "Low", "Volume", "ChangePct",
        "lag_1", "ma_7", "ma_30", "volatility_7"
    ]
    def f(v):
        return float(v) if not pd.isna(v) else 0.0
    data = [[
        int(row["day"]), int(row["month"]), int(row["year"]), int(row["weekday"]),
        f(row["Open"]), f(row["High"]), f(row["Low"]), f(row["Volume"]), f(row["ChangePct"]),
        f(row["lag_1"]), f(row["ma_7"]), f(row["ma_30"]), f(row["volatility_7"])
    ]]
    return {"input_data": {"columns": cols, "index": [0], "data": data}}


def pick_row(df: pd.DataFrame, date_str: str | None) -> Tuple[pd.Series, pd.Timestamp]:
    if date_str:
        d = pd.to_datetime(date_str)
        row = df.loc[df["Date"] == d]
        if row.empty:
            # procurar por mesma data ignorando hora/timezone
            row = df.loc[df["Date"].dt.date == d.date()]
        if row.empty:
            raise ValueError(f"Data {date_str} não encontrada no CSV.")
        row = row.iloc[-1]
        return row, pd.to_datetime(row["Date"])
    else:
        return df.iloc[-1], pd.to_datetime(df.iloc[-1]["Date"])


def main():
    args = parse_args()

    # 1) Carregar e limpar
    df = load_and_clean(args.input)

    # 2) Salvar CSV limpo
    df_clean = df.copy()
    df_clean.to_csv(args.output_csv, index=False)

    # 3) Adicionar features de série temporal (para payload B)
    df_feat = add_time_features(df)

    # 4) Selecionar linha para previsão
    row_A, dA = pick_row(df_clean, args.predict_date)
    row_B, dB = pick_row(df_feat, args.predict_date)

    # 5) Construir payloads
    payload_a = build_payload_A(row_A)
    payload_b = build_payload_B(row_B)

    # 6) Salvar JSONs
    with open(args.json_a, "w", encoding="utf-8") as fa:
        json.dump(payload_a, fa, ensure_ascii=False, indent=2)
    with open(args.json_b, "w", encoding="utf-8") as fb:
        json.dump(payload_b, fb, ensure_ascii=False, indent=2)

    # 7) Log amigável
    print("✅ CSV limpo salvo em:", args.output_csv)
    print("✅ JSON (modelo A) salvo em:", args.json_a)
    print("✅ JSON (modelo B) salvo em:", args.json_b)
    print("ℹ️  Data usada para os payloads:", dA.date())


if __name__ == "__main__":
    try:
        main()
    except Exception as e:
        print("❌ Erro:", e, file=sys.stderr)
        sys.exit(1)
