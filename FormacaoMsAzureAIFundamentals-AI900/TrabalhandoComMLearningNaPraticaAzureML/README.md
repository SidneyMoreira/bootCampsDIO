
# 📊 Previsão do Preço do Bitcoin (BTC-USD) — Roteiro + JSON de Teste (Azure ML)

Este LAB, foi utlizado para criar o projeto de série temporal para **Bitcoin** usando o CSV exportado do **Investing.com** . Para realiza-lo precisa ter pelo menos um conta Trial no Portal Azure para poder explorar as capacidades de Machine Learning da plataforma para desenvolver esta  automação prática. O arquivo CSV até o momento do versionamento possui as seguintes colunas.

- `Date` — data (ex.: `Sep 25, 2025` ou `25/09/2025` ou `09/25/2025`)
- `Price` — preço de fechamento (Close)
- `Open` — abertura
- `High` — máxima
- `Low` — mínima
- `Vol.` — volume (ex.: `62.58K`, `1.2M`)
- `Change %` — variação diária (ex.: `-3.31%` ou `-3,31`)

> **Período informado**: a partir de **18/07/2010**

---

## 0) Execução do script de limpeza e geração de payloads

Use o script `prepare_btc_investing.py` (v2). Ele:
- Detecta **separador `;`** automaticamente.
- Normaliza `Change %` com **vírgula decimal** (`-3,31` → `-0.0331`).
- Converte `Vol.` (K/M/B) para número absoluto.
- Cria *features* de série temporal (`lag_1`, `ma_7`, `ma_30`, `volatility_7`).
- Exporta **JSONs de teste** compatíveis com o **endpoint real-time** do Azure ML.

### Como rodar
```bash
python prepare_btc_investing.py \
  --input btc_investing.csv \
  --output-csv btc_clean.csv \
  --json-a payload_A.json \
  --json-b payload_B.json \
  --predict-date 2025-09-25
```

### Arquivos gerados
- `btc_clean.csv` — dataset limpo e ordenado por data.
- `payload_A.json` — payload **sem** features de série temporal.
- `payload_B.json` — payload **com** features de série temporal.

> Se `--predict-date` não for fornecido, o script usa a **última data** do CSV.

---

## 1) Pré-processamento (resumo do que o script faz)

1. **Datas**
   - Converte `Date` para `YYYY-MM-DD`.
   - Cria: `day`, `month`, `year`, `weekday` (0=segunda … 6=domingo).

2. **Numéricos**
   - Remove vírgulas de milhares em `Price`, `Open`, `High`, `Low`.
   - Converte `Vol.` → `Volume` absoluto (K/M/B → ×1e3/×1e6/×1e9).
   - Converte `Change %` para decimal: `-3.31%` ou `-3,31` → `-0.0331` (coluna `ChangePct`).

3. **Target**
   - `Price` é a variável-alvo (`y`). **Não** enviar `Price` no payload de teste.

4. **Features de série temporal (para modelo B)**
   - `lag_1`, `ma_7`, `ma_30`, `volatility_7`.

---

## 2) Documentação (configuração do experimento no Azure ML)

#### Basic settings:
  * **Job name**: BTC-serie-historica <br>
  * **New experiment name**: Previsao-BTC <br>
  * **Description**: Machine Learning para previsão do preço do Bitcoin (BTC) baseado na série histórica registrada pela Investing.com <br>
  * **Tags**: none  

#### Task type & data:
  * **Select task type**: Regression  
  * **Select dataset**: Foi utilizado o arquivo **`btc_clean.csv`** (presente nesse projeto) baixado e tratado a partir do site [Investing.com](https://www.investing.com/crypto/bitcoin/historical-data)  
  * **Data type name**: btc_clean  
  * **Data type description**: Série histórica do BTC limpa e preparada para treino  
  * **Data type**: Tabular  
  * **Data type source**: From local files  
  * **Destination storage type**: Azure Blob Storage  
  * **Destination storage type name**: workspaceblobstore  
  * **MLtable selection**: Upload file **`btc_clean.csv`**  

#### Dataset settings:
  * **Task type**: Upload file **`btc_clean.csv`**  
  * **Dataset**: Upload file **`btc_clean.csv`**  
  * **Target column**: Price  

#### Additional configuration settings:
  * **Primary metric**: NormalizedRootMeanSquaredError  
  * **Explain best model**: Unselected  
  * **Enable ensemble stacking**: Unselected  
  * **Use all supported models**: Unselected (foi restringido apenas a alguns modelos para reduzir tempo de execução)  
  * **Allowed models**: RandomForest e LightGBM  

#### Limits:
  * **Max trials**: 3  
  * **Max concurrent trials**: 3  
  * **Max nodes**: 3  
  * **Metric score threshold**: 0.085  
  * **Experiment timeout**: 15  
  * **Iteration timeout**: 15  
  * **Enable early termination**: Selected  

#### Validation and test:
  * **Validation type**: Train-validation split  
  * **Percentage of validation data**: 10  
  * **Test dataset**: None  

#### Compute:
  * **Select compute type**: Serverless  
  * **Virtual machine type**: CPU  
  * **Virtual machine tier**: Dedicated  
  * **Virtual machine size**: Standard_DS3_V2  
  * **Number of instances**: 1  

---

## 3) Escolha correta das colunas no treino (MUITO IMPORTANTE)

O modelo só vai aceitar no endpoint as **mesmas colunas** que você usou no treino.  
- Se você treinou com o **dataset cru** (sem limpeza), o endpoint vai esperar colunas como `Date`, `Vol.`, `Change %`.  
- Se você treinou com o **dataset limpo** (`btc_clean.csv`), o endpoint vai esperar colunas já processadas (`day, month, year, weekday, Open, High, Low, Volume, ChangePct`).  

➡️ **Recomendado**: sempre usar o `btc_clean.csv` para treinar, marcando manualmente no AutoML as colunas que farão parte do modelo:  
- Para **Payload A**:  
  `day, month, year, weekday, Open, High, Low, Volume, ChangePct`  
- Para **Payload B**:  
  `day, month, year, weekday, Open, High, Low, Volume, ChangePct, lag_1, ma_7, ma_30, volatility_7`  

> Assim você garante que o **payload gerado pelo script** será aceito sem erros no endpoint.

---

## 4) Implantar e testar o modelo

Na guia **Modelo** do melhor modelo treinado pelo AutoML, selecione **Implantar** e use a opção **Ponto de extremidade em tempo real** com as seguintes configurações:

* Compute:
  * **Virtual machine**: Standard_DS3_v2  
  * **Instance count**: 3  
* Endpoint:
  * **Endpoint**: New  
  * **Endpoint name**: deixar o padrão ou escolher um nome globalmente único  
  * **Deployment name**: deixar o padrão  
* Outras opções:
  * **Inferencing data collection**: Disabled  
  * **Package Model**: Disabled  

Após a implantação, vá em **Endpoints > [Seu endpoint] > Test** e cole o payload adequado (A ou B).

---

## 5) JSON para testar o endpoint (Modelo A — sem features de série temporal)

**Schema (ordem importa):**
```
[
  "day","month","year","weekday",
  "Open","High","Low","Volume","ChangePct"
]
```

**Exemplo (para 2025-09-25; valores ilustrativos):**
```json
{
  "input_data": {
    "columns": [
      "day","month","year","weekday",
      "Open","High","Low","Volume","ChangePct"
    ],
    "index": [0],
    "data": [
      [25, 9, 2025, 3, 113307.1, 113508.6, 108675.3, 62580, -0.0331]
    ]
  }
}
```

---

## 6) JSON para testar o endpoint (Modelo B — com features de série temporal)

**Schema (ordem importa):**
```
[
  "day","month","year","weekday",
  "Open","High","Low","Volume","ChangePct",
  "lag_1","ma_7","ma_30","volatility_7"
]
```

**Exemplo (valores fictícios de features):**
```json
{
  "input_data": {
    "columns": [
      "day","month","year","weekday",
      "Open","High","Low","Volume","ChangePct",
      "lag_1","ma_7","ma_30","volatility_7"
    ],
    "index": [0],
    "data": [
      [25, 9, 2025, 3, 113307.1, 113508.6, 108675.3, 62580, -0.0331,
       113950.2, 114210.5, 112780.9, 1050.3]
    ]
  }
}
```

> Calcule essas features antes de enviar (o script já faz isso quando gera o `payload_B.json`).

---

## 7) Checklist de compatibilidade
- ✅ Mesmas **colunas** e **tipos** do treino.  
- ✅ `Price` **não** vai no payload (é o alvo).  
- ✅ `Volume` é número absoluto; `ChangePct` é **decimal**.  
- ✅ Datas decompostas em `day/month/year/weekday` (se usadas no treino).

---

## 8) Teste via REST (cURL)
```bash
curl -X POST "$ENDPOINT_URL/score" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d @payload_A.json
```
> Substitua por `payload_B.json` se seu modelo usar features de série temporal.

---

## 9) Próximos passos
- Versionar `btc_clean.csv` e `payload_*.json`.  
- Agendar atualização diária do CSV e *retrain*.  
- Publicar previsões em API/Function ou dashboard (Power BI).
