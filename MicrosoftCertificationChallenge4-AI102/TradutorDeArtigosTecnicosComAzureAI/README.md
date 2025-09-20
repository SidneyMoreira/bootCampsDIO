# Desafio de Código - Tradutor de Artigos Técnicos com AzureAI

Este repositório contém dois notebooks Jupyter que demonstram abordagens complementares para traduzir conteúdo técnico utilizando serviços da Azure AI.

- `TranslatorIA_Dio.ipynb`: traduz documentos `.docx` (parágrafo a parágrafo) com a API Microsoft Translator Text.
- `TranslateArticleIA_DIO.ipynb`: extrai texto bruto de um artigo na web e traduz usando Azure OpenAI (via LangChain).

## Pré‑requisitos

- Python 3.8+ com Jupyter (ou Google Colab).
- Acesso a serviços Azure:
  - Microsoft Translator (chave e região).
  - Azure OpenAI (endpoint, chave, versão da API e um deployment, ex.: `gpt-4o-mini`).
- Conexão com a internet para instalar dependências e acessar APIs/artigos.

## Instalação rápida (no próprio notebook)

Ambos os notebooks possuem a primeira célula com as instalações necessárias:

- `TranslatorIA_Dio.ipynb`: `!pip install requests python-docx`
- `TranslateArticleIA_DIO.ipynb`: `!pip install requests beautifulsoup4 openai langchain-openai`

Você pode executar essas células diretamente no Jupyter/Colab para preparar o ambiente.

## 1) Traduzir documentos `.docx` com Microsoft Translator

Notebook: `TranslatorIA_Dio.ipynb`

### Como funciona

- Usa a API REST do Microsoft Translator (`/translate`) para traduzir texto do inglês para o português do Brasil (`pt-br`).
- Lê o documento `.docx` com `python-docx`, traduz cada parágrafo e grava um novo arquivo com sufixo do idioma (`_pt-br.docx`).

### Configuração necessária

Edite as variáveis na célula de configuração e substitua os placeholders:

- `subscription_key = "YOUR_SUBSCRIPTION_KEY"`
- `location = 'YOUR_LOCATION'` (por exemplo: `brazilsouth`, `eastus` etc.)
- `target_language = 'pt-br'` (ajuste se desejar outro idioma)

Também ajuste o caminho do arquivo de entrada `.docx` na última célula:

- `input_file = "/content/SeuArquivo.docx"`

### Execução

1. Execute a célula de instalação de pacotes.
2. Informe sua chave, região e idioma alvo.
3. Rode a célula que define a função `translate_document`.
4. Defina `input_file` com o caminho do `.docx` e execute a tradução.

Saída esperada: um novo arquivo `.docx` com o sufixo do idioma (ex.: `SeuArquivo_pt-br.docx`).

## 2) Traduzir artigos da web com Azure OpenAI

Notebook: `TranslateArticleIA_DIO.ipynb`

### Como funciona

- Extrai o texto “limpo” de uma URL usando `requests` + `BeautifulSoup`.
- Envia o texto para um modelo do Azure OpenAI via `langchain-openai` (`AzureChatOpenAI`) solicitando a tradução para o idioma desejado e resposta em Markdown.

### Configuração necessária

Na célula que cria o cliente `AzureChatOpenAI`, configure corretamente:

- `azure_endpoint = "https://SEU-ENDPOINT.openai.azure.com"`
- `api_key = "SUA_CHAVE"`
- `api_version = "2024-02-15-preview"` (ou a versão que você utiliza)
- `deployment_name = "SEU-DEPLOYMENT"` (ex.: `gpt-4o-mini` ou equivalente configurado no seu recurso)

Observações importantes:

- Garanta que os parâmetros/nomes estejam corretos (ex.: `system` em vez de `sytem`, `max_tokens` caso queira limitar a saída etc.).
- Dependendo do tamanho do artigo, pode ser necessário paginar/segmentar o texto antes de enviar ao modelo para evitar limites de tokens.

### Execução

1. Execute a célula de instalação de pacotes.
2. Ajuste as credenciais do Azure OpenAI e o `deployment_name`.
3. Rode a célula `extract_text_from_url(url)` com a URL do artigo desejado.
4. Use `translate_article(text, 'pt-br')` para obter a tradução em Markdown.

Saída esperada: a tradução impressa/retornada no output do notebook (em Markdown), que você pode salvar/colar conforme necessidade.

## Boas práticas e dicas

- Segurança: evite comitar chaves e segredos. Prefira variáveis de ambiente ou armazenar valores sensíveis de forma segura.
- Idiomas: para outros idiomas, ajuste `target_language` (Translator) e o parâmetro `lang` em `translate_article`.
- Qualidade do texto: artigos web podem conter ruídos (menus, rodapés). Revise/ajuste a limpeza de HTML se necessário.
- Custos e limites: verifique as cotas/limites do seu recurso Azure (Translator e OpenAI) e trate erros HTTP (429/401/403).

## Estrutura do repositório

- `TranslatorIA_Dio.ipynb` — tradução de `.docx` com Microsoft Translator.
- `TranslateArticleIA_DIO.ipynb` — extração de texto da web e tradução com Azure OpenAI.

## Problemas comuns

- 401/403 ao chamar APIs: verifique `api_key`, `subscription_key` e `location/endpoint`.
- 404 no endpoint: confirme a URL base e a `api_version` do Azure OpenAI.
- Saída vazia ou truncada: o texto pode ter excedido limites do modelo; tente dividir o conteúdo.
- Caracteres estranhos: garanta a codificação UTF‑8 e corrija acentuação/roles (por exemplo `system` ao definir mensagens).

---

Sinta-se à vontade para adaptar os notebooks às suas necessidades (por exemplo, segmentar textos longos, salvar a saída em `.md`, tratar imagens/links, etc.).

