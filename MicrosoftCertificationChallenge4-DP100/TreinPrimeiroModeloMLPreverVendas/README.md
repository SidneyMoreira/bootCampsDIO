# Prevendo Vendas de Sorvete com Azure Machine Learning (DP-100 DIO)

Projeto prático de Machine Learning para prever a quantidade de sorvetes vendidos a partir da temperatura do dia. Este repositório compõe a entrega do desafio da DIO (DP-100) e foi estruturado para ser reproduzido no Azure Machine Learning (Designer e AutoML), com orientações para rastrear experimentos via MLflow e implantar o melhor modelo em tempo real.


## 1) Cenário
Você é dono da sorveteria Gelato Mágico, em uma cidade litorânea. As vendas variam conforme a temperatura do dia. Sem uma previsão confiável, há risco de produzir sorvete demais (desperdício) ou de menos (perda de vendas). A proposta é treinar um modelo de regressão que estime a demanda diária com base na temperatura, apoiando o planejamento de produção.


## 2) Objetivos do Desafio
- Treinar um modelo de ML para prever vendas de sorvetes com base na temperatura do dia.
- Registrar e gerenciar o modelo usando MLflow/Azure ML.
- Implementar o modelo para previsões em tempo real em cloud (Azure ML Endpoints).
- Criar um pipeline estruturado para treinar e testar o modelo, garantindo reprodutibilidade.


## 3) Dados
- Arquivo: `inputs/Tabela_de_Vendas_de_Sorvete.csv`
- Colunas exemplo: `Data`, `Qtd. Vendas` (alvo), `Temperatura (°C)` (feature principal).
- Tarefa: Regressão (prever `Qtd. Vendas`).


## 4) Pré‑Requisitos
- Assinatura Azure e um Resource Group para o workspace.
- Azure Machine Learning Workspace (Studio/SDK).
- (Opcional) Ambiente local com Python 3.10+, `pandas`, `scikit-learn`, `mlflow`, `azure-ai-ml` para experimentos locais.


## 5) Estrutura do Repositório
- `inputs/` — contém o dataset de vendas.
- `img/` — capturas de tela do ambiente e resultados.
- `README.md` — guia do projeto e passo a passo.


## 6) Ambiente no Azure ML
- Resource Group criado para os recursos do projeto:
  
  ![RG](/img/RG-Projects.png)

- Cluster de computação (ex.: `STANDARD_DS11_V2`) para executar os jobs:
  
  ![Cluster](/img/Cluster.png)


## 7) Treinamento no Designer (Low-Code)
1. No Azure ML Studio, crie um novo pipeline no Designer.
2. Importe o dataset `Tabela_de_Vendas_de_Sorvete.csv`.
3. Selecione colunas relevantes (por ex.: `Temperatura (°C)` → feature; `Qtd. Vendas` → label).
4. Divida os dados (Split Data), treine um modelo de Regressão (ex.: Linear Regression) e avalie (Evaluate Model).
5. Execute o pipeline e verifique as métricas.

Tela do Designer:

![Designer](/img/TelaDesigner.png)

Scores obtidos após a execução:

![Scores](/img/ScoreDesigner.png)


## 8) Treinamento com AutoML (Code/No-Code)
1. Crie uma nova execução de AutoML para Regressão.
2. Defina: Dataset = `Tabela_de_Vendas_de_Sorvete.csv`, Target = `Qtd. Vendas`, Primary metric = (ex.: `r2_score`).
3. Inclua `Temperatura (°C)` como feature principal; opcionalmente adicione outras features, se existirem.
4. Execute e aguarde a seleção do melhor algoritmo.

Resultado (exemplo): AutoML escolheu PCA + XGBoostRegressor como melhor modelo.

![Melhor Algoritmo](/img/MelhorAlgoritmo.png)

Métricas consolidadas:

![Métricas](/img/Metricas.png)


## 9) Rastreamento com MLflow
No Azure ML, os experimentos podem ser rastreados com MLflow. Exemplo mínimo em Python (execução local ou em script no Azure ML):

```python
import mlflow
import mlflow.sklearn
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score, mean_absolute_error

df = pd.read_csv("inputs/Tabela_de_Vendas_de_Sorvete.csv")
X = df[["Temperatura (°C)"]]
y = df["Qtd. Vendas"]
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

with mlflow.start_run(run_name="baseline-linear-regression"):
    model = LinearRegression()
    model.fit(X_train, y_train)

    preds = model.predict(X_test)
    r2 = r2_score(y_test, preds)
    mae = mean_absolute_error(y_test, preds)

    mlflow.log_metric("r2", r2)
    mlflow.log_metric("mae", mae)
    mlflow.sklearn.log_model(model, artifact_path="model")
```

Ao executar em um job do Azure ML, os logs e artefatos ficam automaticamente associados ao experimento, facilitando comparação e registro do melhor modelo.


## 10) Implantação em Tempo Real (Managed Online Endpoint)
1. No Azure ML Studio, registre o melhor modelo do AutoML/Designer.
2. Crie um Managed Online Endpoint e um deployment (ex.: `blue`) apontando para o modelo.
3. Defina o schema de entrada (JSON com `Temperatura (°C)`).
4. Publique o endpoint e teste no painel do Studio ou via `curl`/SDK.

Exemplo de payload:

```json
{
  "input_data": {
    "columns": ["Temperatura (°C)"],
    "index": [0],
    "data": [[28]]
  }
}
```


## 11) Pipeline e Reprodutibilidade
Estruture um pipeline com etapas:
- Ingestão/validação do dataset
- Pré-processamento (opcional)
- Treinamento
- Avaliação/seleção do melhor modelo
- Registro do modelo
- (Opcional) Implantação automatizada

Isso garante execuções repetíveis, controle de versões e comparabilidade entre experimentos.


## 12) Resultados e Insights
- O AutoML indicou PCA + XGBoostRegressor como melhor combinação no conjunto testado.
- As métricas e artefatos permitem comparar com o baseline (ex.: Regressão Linear no Designer).
- O endpoint em tempo real possibilita estimar demanda diária e orientar a produção.


## 13) Próximos Passos (Sugestões)
- Enriquecer dados com clima previsto (sensação térmica, umidade, chuva) e sazonalidade.
- Tunar hiperparâmetros do XGBoost para ganhos adicionais.
- Adicionar monitoramento de drift e re‑treino programado.
- Versionar datasets e modelos com policies de aprovação.


---

Referências visuais:
- `img/TelaDesigner.png` — Tela do Designer
- `img/ScoreDesigner.png` — Scores do Designer
- `img/MelhorAlgoritmo.png` — Resultado do AutoML
- `img/Metricas.png` — Métricas do AutoML
