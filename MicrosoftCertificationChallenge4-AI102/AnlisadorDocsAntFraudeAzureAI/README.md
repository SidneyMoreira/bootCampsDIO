# Desafio de Projeto AI-102 - Análise de Documentos Anti-fraude com AzureAI

## Visão Geral
Nesse projeto foi implementado uma solução de análise automatizada de documentos utilizando AzureAI para identificar padrões de fraude, validar autenticidade e aumentar a segurança de transações e processos empresariais, garantindo maior confiabilidade no processamento de documentos sensíveis.

O repositório contém um app simples em Streamlit que:
- Faz upload de imagens/PDFs para o Azure Blob Storage.
- Usa o Azure AI Document Intelligence (modelo prebuilt-creditCard) para extrair e validar informações de cartões.
- Exibe o resultado da verificação (válido/inválido) e os campos extraídos.

Observação: O projeto pode ser estendido para uso de Azure OpenAI em etapas de análise contextual, explicações e detecção de fraudes avançadas.

## Componentes Principais
- Azure AI Document Intelligence (Form Recognizer) – extração/validação de campos de cartão.
- Azure Blob Storage – armazenamento dos arquivos enviados.
- Streamlit – interface web para upload e visualização.
- Python 3.9+ e bibliotecas do SDK Azure.
- (Opcional) Azure Key Vault – gerenciamento de segredos.
- (Opcional) Azure OpenAI – enriquecimento e análise contextual.

## Estrutura do Projeto
- `src/app.py` – interface Streamlit (upload, chamada de serviços, exibição).
- `src/services/blob_service.py` – upload para Azure Blob Storage.
- `src/services/credit_card_service.py` – análise com Document Intelligence.
- `src/utils/Config.py` – carregamento de variáveis de ambiente.
- `src/requirements.txt` – dependências Python.

## Pré‑requisitos
- Assinatura Azure com recursos:
  - Azure AI Document Intelligence (endpoint e chave).
  - Azure Storage Account (Blob) e um container criado.
  - (Opcional) Azure Key Vault para segredos.
- Python 3.9 ou superior
- Pip e virtualenv (recomendado)

## Configuração
1) Clone o repositório e entre na pasta do projeto.
2) (Opcional) Crie e ative um ambiente virtual.
3) Instale as dependências:
   - `pip install -r src/requirements.txt`
4) Crie um arquivo `.env` na raiz do projeto com as variáveis abaixo.

## Variáveis de Ambiente (.env)
Preencha os valores conforme seus recursos no Azure:

```
# Azure AI Document Intelligence (Form Recognizer)
ENDPOINT="https://<seu-recurso>.cognitiveservices.azure.com/"
AZURE_FORM_RECOGNIZER_KEY="<sua_chave_do_document_intelligence>"

# Azure Storage (Blob)
AZURE_STORAGE_CONNECTION_STRING="<sua_connection_string_do_storage>"
AZURE_STORAGE_CONTAINER_NAME="<nome_do_container>"
# Algumas partes do código usam CONTAINER_NAME; por compatibilidade, defina também:
CONTAINER_NAME="<nome_do_container>"
```

Notas importantes sobre o código atual:
- `src/services/credit_card_service.py` espera recuperar a chave via `Config.get_secret("AZURE_FORM_RECOGNIZER_KEY")`. Caso você não utilize Key Vault, ajuste `src/utils/Config.py` para expor diretamente `AZURE_FORM_RECOGNIZER_KEY` a partir do `.env` ou adapte a chamada no service para usar `os.getenv`.
- `src/services/blob_service.py` referencia `Config.AZURE_STORAGE_CONTAINER_NAME`, enquanto `src/utils/Config.py` define `CONTAINER_NAME`. Para evitar erros, mantenha as duas variáveis no `.env` (ou alinhe os nomes no código).

## Como Executar
- Execute a aplicação Streamlit:
  - `streamlit run src/app.py`
- No navegador, envie uma imagem/PDF de cartão (png, jpg, jpeg, pdf).
- O app fará o upload para o Blob Storage e iniciará a análise com o modelo `prebuilt-creditCard`.
- A tela exibirá o status (Válido/Inválido) e os campos extraídos (nome, emissor, validade, etc.).

## Boas Práticas de Segurança
- Nunca faça commit do `.env`.
- Prefira Azure Key Vault para armazenar chaves/segredos em produção.
- Restrinja permissões do Storage e use SAS/identidade gerenciada quando possível.

## Licença
Este projeto é licenciado sob a Licença MIT. Consulte o arquivo `LICENSE` para detalhes.

