
# Desafio Criando um Dashboard de Vendas do Xbox Game Pass

> **Resumo**: Este repositório/documentação faz parte do Desafio proposto no Bootcamp Santander Execel IA e descreve a base de assinaturas do Xbox Game Pass e os cálculos/dashboards construídos diretamente no arquivo **Base_XBox.xlsx**. Ele serve como material de estudo e demonstração de análise com Tabelas Dinâmicas e segmentações de dados no Excel.

---

## 📦 Arquivo principal
- **Nome**: `Base_XBox.xlsx`
- **Planilhas**:
  - `Assets`: paleta de cores e referências visuais (brand book simples).
  - `Bases`: dados tabulares de assinantes e add-ons (EA Play e Minecraft).
  - `Cálculos`: perguntas de negócio e Tabelas Dinâmicas de suporte.
  - `Dashboard`: página de visualização (cards/indicadores).  

> Observação: O arquivo contém imagens/ícones utilizados no dashboard e segmentações de dados (slicers) compatíveis com Excel 2010+.  

---

## 🗂️ Estrutura dos dados (`Bases`)
Cada linha representa uma **assinatura** (subscriber-plan) com informações de plano, preço, período e add-ons.

### Colunas
- `Subscriber ID` — Identificador numérico do assinante.
- `Name` — Nome do assinante (dados fictícios).
- `Plan` — Categoria do plano (`Core`, `Standard`, `Ultimate`).
- `Start Date` — Data de início da assinatura (formato MM/DD/YYYY).
- `Auto Renewal` — Indicador de renovação automática (`Yes`/`No`).
- `Subscription Price` — Preço base do plano (USD ou BRL, conforme contexto; números no dataset).
- `Subscription Type` — Frequência de cobrança (`Monthly`, `Quarterly`, `Annual`).
- `EA Play Season Pass` — Indicador de compra do add-on EA Play (`Yes`/`No`).
- `EA Play Season Pass Price` — Valor do add-on EA Play quando aplicável.
- `Minecraft Season Pass` — Indicador de compra do add-on Minecraft (`Yes`/`No`).
- `Minecraft Season Pass Price` — Valor do add-on Minecraft quando aplicável.
- `Coupon Value` — Descontos aplicados na transação.
- `Total Value` — Total faturado por linha (preço do plano + add-ons − cupons).

> **Privacidade**: nomes são gerados para fins didáticos; não há PII real.  

### Dicionário de valores relevantes
- **Plan**: `Core` (entrada), `Standard` (intermediário), `Ultimate` (premium).  
- **Subscription Type**: `Monthly`, `Quarterly`, `Annual`.  
- **Flags**: colunas booleanas usam `Yes`/`No`.  

---

## 📊 Perguntas de negócio & resultados (planilha `Cálculos`)
As Tabelas Dinâmicas respondem às perguntas abaixo:

1) **Faturamento total de vendas de planos anuais (agregado)**: **1.754** (soma de `Total Value` filtrando `Subscription Type = Annual`).  
2) **Faturamento total de planos anuais por Auto-Renewal**:  
   - `Yes`: **1.537**  
   - `No`: **217**  
   - **Total**: **1.754**  

> Os números acima reproduzem a Tabela Dinâmica existente na planilha `Cálculos` com rótulos de linha por `Auto Renewal` e soma de `Total Value`, filtrada em `Subscription Type = Annual`.  

---

## 🎨 Assets (planilha `Assets`)
Paleta de cores sugerida no arquivo (hex):
- `#9BC848` — Xbox Color  
- `#22C55E` — Xbox Color  
- `#2AE6B1` — Menus  
- `#5BF6A8` — Menus  
- `#E8E6E9` — Negative zone

Além disso, o arquivo inclui **logos** e **ícones** usados no `Dashboard`.

---

## 🧪 Reproduzindo os cálculos no Excel
> Requer **Microsoft Excel 2010 ou superior** para Tabelas Dinâmicas e Segmentações de Dados.

1. Abra `Base_XBox.xlsx` e vá para a planilha **`Cálculos`**.
2. Na Tabela Dinâmica principal, defina o filtro **`Subscription Type = Annual`**.
3. Em **`Rótulos de Linha`**, mantenha **`Auto Renewal`**.
4. Em **`Valores`**, utilize **`Soma de Total Value`**.
5. Confira os totais (Yes/No/Total) conforme a seção *Perguntas de negócio*.

Para explorar outras visões (ex.: por `Plan`, por add-on):
- Adicione `Plan` em `Colunas` para comparar **Core x Standard x Ultimate**.
- Troque `Rótulos de Linha` para `EA Play Season Pass` ou `Minecraft Season Pass` e mantenha `Soma de Minecraft Season Pass Price` / `Soma de EA Play Season Pass Price` nos valores.

### Segmentações (Slicers)
- Utilize segmentações de `Subscription Type`, `Plan` e `Auto Renewal` (quando disponíveis) para navegação rápida. No Excel, menu **Inserir > Segmentação de Dados** e aponte para a Tabela Dinâmica correspondente.

---

## 📈 Recriando o Dashboard
A planilha **`Dashboard`** apresenta cartões com totais e elementos visuais.

Para montar do zero:
1. Insira **Tabelas Dinâmicas** baseadas na tabela de `Bases` para cada indicador (ex.: faturamento anual, totais por add-on).
2. Formate os números como moeda (BRL) ou número simples, conforme necessidade.
3. Adicione **segmentações** conectadas a múltiplas Tabelas Dinâmicas (menu **Relatório de Conexões** na Segmentação) para filtrar tudo em conjunto.
4. Use os **assets** da planilha `Assets` (cores e ícones) para manter consistência visual.

> Dica: utilize **Cartões** (formas) vinculados a células com GETPIVOTDATA ou referências diretas às células de totais.

---

## ♻️ Boas práticas para reprodutibilidade
- Mantenha a tabela da planilha `Bases` como **Tabela do Excel** (Ctrl+T) para facilitar a atualização das Tabelas Dinâmicas.
- Ao incluir novos dados (novas linhas), atualize todas as Tabelas Dinâmicas (**Dados > Atualizar Tudo**).
- Padronize valores de `Yes/No` e datas (`MM/DD/YYYY`) para evitar quebras de segmentação.

---

## ⚠️ Limitações
- Os valores são **didáticos** e podem não refletir preços reais/tributos.
- Nomes de pessoas são fictícios.
- O arquivo usa imagens incorporadas; abrir em versões antigas pode perder formatação.

---

## 📮 Contato
- Maintainer: **Sidnei Moreira**
- Área: QA / Automação de Testes / Data Analyst

