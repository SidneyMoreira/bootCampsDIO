## Detecção de Objetos/Pessoas com Azure AI Vision (Image Analysis v4)

Este README descreve o uso do `app.py`, que realiza detecção de objetos/pessoas usando a API REST de Image Analysis v4 do Azure AI Vision.

## O que o app faz
- Analisa imagens por URL, por um arquivo local (`--image-path`) ou por um diretório (`--inputs-dir`, processando todas as imagens).
- Exibe um resumo legível no console (bounding boxes e tag mais provável) e permite salvar o resultado em JSON.
- Caso nenhum caminho/URL seja informado, usa uma URL de exemplo padrão.

## Pré‑requisitos
1) Python 3.8 ou superior
2) Dependências Python:
   - `pip install requests`
3) Recurso do Azure AI Vision (Image Analysis v4) com chave e endpoint
4) Variáveis de ambiente:
   - `VISION_ENDPOINT=https://<seu-recurso>.cognitiveservices.azure.com`
   - `VISION_KEY=<sua-chave>`

No Windows (PowerShell):
- `setx VISION_ENDPOINT https://<seu-recurso>.cognitiveservices.azure.com`
- `setx VISION_KEY <sua-chave>`
(Reinicie o terminal)

No macOS/Linux (Shell):
- `export VISION_ENDPOINT=https://<seu-recurso>.cognitiveservices.azure.com`
- `export VISION_KEY=<sua-chave>`
(Válido para a sessão atual)

## Execução
URL de exemplo padrão:
- `python app.py`

URL própria e salvando em JSON:
- `python app.py --image-url https://endereco/da/imagem.jpg --output outputs/resultado-objetos.json`

Arquivo local único:
- `python app.py --image-path inputs/foto.jpg --output outputs/foto.objects.json`

Processar todas as imagens de um diretório:
- `python app.py --inputs-dir inputs --outputs-dir outputs`

Saída no console inclui:
- Dimensões da imagem, total de objetos e a bounding box de cada objeto
- A tag mais provável (nome e confiança) para cada detecção

Notas:
- Tipos de arquivo aceitos por padrão em diretórios: `.jpg`, `.jpeg`, `.png`, `.bmp`, `.gif`.
- Quando `--inputs-dir` é usado, cada imagem gera um arquivo `*.objects.json` no diretório definido por `--outputs-dir` (padrão: `outputs`).

## Endpoint utilizado
`POST {VISION_ENDPOINT}/computervision/imageanalysis:analyze?features=objects&api-version=2023-10-01`

- Para URL: `Content-Type: application/json` com corpo `{ "url": "..." }` (um campo `uri` redundante também é enviado para compatibilidade).
- Para arquivo local: `Content-Type: application/octet-stream` com os bytes da imagem.

