## Análise com Azure AI (Foundry/Language Studio)

Este repositório contém dois scripts em Python:
- Análise de Sentimentos para textos entre aspas em `inputs/sentences.txt`.
- Transcrição de áudios em `inputs/` seguida de tradução para o idioma desejado.

## Pré‑requisitos
- Python 3.9+
- Recursos do Azure AI (no Azure AI Foundry/Studio ou Portal do Azure):
  - Azure AI Language (Text Analytics) para Sentiment Analysis
  - Azure Speech (Speech to Text) para Transcrição
  - Azure Translator (Text Translation) para Tradução

## Instalação
- `pip install -r requirements.txt`
- Opcional: copie `.env.example` para `.env` e preencha os valores.

## Variáveis de ambiente
- Azure AI Language (Sentimentos):
  - `AZURE_LANGUAGE_ENDPOINT`
  - `AZURE_LANGUAGE_KEY`
  - Alternativas aceitas pelo app: `AZURE_AI_ENDPOINT`/`AZURE_AI_KEY`, `AZURE_AI_SERVICE_ENDPOINT`/`AZURE_AI_SERVICE_KEY`.
- Azure Speech (Transcrição):
  - Use `AZURE_SPEECH_KEY` e `AZURE_SPEECH_REGION` (ou `SPEECH_KEY`/`SPEECH_REGION`).
  - Em Azure AI Foundry, pegue a “Chave do recurso” e a “Região” da conexão do Speech. Não use o endpoint multi-serviços (`*.cognitiveservices.azure.com`) com o Speech SDK.
  - Opcional: `AZURE_SPEECH_ENDPOINT` somente se for um endpoint específico de Speech (`*.stt.speech.microsoft.com`). Se você informar um endpoint multi-serviços, o app fará fallback para usar `AZURE_SPEECH_REGION`.
- Azure Translator (Tradução):
  - `AZURE_TRANSLATOR_KEY`
  - Opcional: `AZURE_TRANSLATOR_REGION` (normalmente a mesma região do recurso AI Services/Translator)
  - Opcional: `AZURE_TRANSLATOR_ENDPOINT` (default: `https://api.cognitive.microsofttranslator.com`)

---

## App 1: Análise de Sentimentos
- Script: `app.py`
- O que faz: Lê `inputs/sentences.txt`, extrai apenas os trechos entre aspas duplas e envia para Análise de Sentimentos do Azure AI Language. Gera saída em Markdown.

### Uso
- Padrão (lê `inputs/sentences.txt`, idioma `pt`, e salva em `outputs/sentiment_results.md`):
  - `python app.py`
- Personalizado (arquivo, idioma e saída):
  - `python app.py --file inputs/sentences.txt --lang pt --out outputs/sentiment_results.md`

### Saída
- Console: rótulo (positive/neutral/negative) e scores.
- Arquivo: tabela Markdown com colunas `# | Texto | Sentimento | Positivo | Neutro | Negativo`.

---

## App 2: Transcrição + Tradução
- Script: `transcribe_translate.py`
- O que faz: Transcreve todos os áudios na pasta `inputs/` e traduz o texto transcrito para o idioma desejado, gerando uma tabela Markdown em `outputs/`.

### Formatos aceitos
- `.wav`, `.mp3`, `.ogg`, `.m4a`, `.flac`, `.wma`, `.mp4`.

### Uso
- Padrão (lê `inputs/`, auto‑detecta entre `pt-BR,en-US,es-ES`, traduz para inglês e salva em `outputs/transcriptions.md`):
  - `python transcribe_translate.py`
- Definindo idioma do áudio e destino:
  - `python transcribe_translate.py --speech-lang pt-BR --to es`
- Processando arquivos específicos (um ou N arquivos, com glob):
  - Um arquivo: `python transcribe_translate.py --files inputs/meu_audio.wav --to en`
  - Vários arquivos: `python transcribe_translate.py --files inputs/*.wav inputs/*.mp3 --to en`
- Personalizando pasta de entrada, auto‑detecção e saída:
  - `python transcribe_translate.py --inputs inputs --detect pt-BR,en-US --out outputs/minhas_transcricoes.md --to en`

### Saída
- Tabela Markdown com colunas: `# | Arquivo | Idioma | Texto | Tradução (<destino>) | Erro`.

## Observações
- Salve arquivos de texto em UTF-8 para preservar acentuação.
- No Azure AI Foundry/Studio, conecte os recursos correspondentes (Language, Speech e Translator) ao seu projeto. Preencha no `.env` assim:
  - Language: use `endpoint` + `key` exibidos.
  - Speech: use `key` + `region` exibidos. Deixe `AZURE_SPEECH_ENDPOINT` em branco a menos que você tenha um endpoint do tipo `stt.speech.microsoft.com`.
  - Translator: use `key` e, se seu recurso exigir, a `region` correspondente.
