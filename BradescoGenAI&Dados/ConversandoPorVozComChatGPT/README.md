
# Projeto de Assistente de Voz Inteligente

### Sobre o Projeto
Este projeto desenvolve um assistente de voz interativo utilizando tecnologias de ponta para conversão de fala em texto (Speech-to-Text) e texto em fala (Text-to-Speech), integrado a um modelo de linguagem avançado para respostas inteligentes. Ele permite a interação por voz em múltiplos idiomas, mantendo o contexto da conversa e oferecendo uma experiência de usuário dinâmica e personalizável.

### Funcionalidades Implementadas
- **Gravação de Áudio Interativa**: Interface de usuário (UI) construída com `ipywidgets` para iniciar, parar e reproduzir gravações de áudio diretamente no Google Colab.
- **Transcrição de Fala (Speech-to-Text)**: Utiliza o modelo Whisper (OpenAI) para converter áudio gravado em texto, com seleção dinâmica de modelos (tiny, base, small, medium, large) para otimização de performance ou precisão.
- **Processamento de Linguagem Natural (NLP)**: Integração com o modelo Gemini (Google Generative AI) para gerar respostas contextuais e inteligentes com base na transcrição do usuário. Suporte a conversas multi-turno.
- **Síntese de Voz (Text-to-Speech)**: Converte as respostas do Gemini em áudio utilizando a biblioteca gTTS (Google Text-to-Speech), permitindo que o assistente 'fale' suas respostas.
- **Seleção Dinâmica de Idiomas**: Permite ao usuário escolher o idioma de interação (Português, Inglês, Espanhol, Francês, Alemão), que afeta tanto a transcrição quanto a síntese de voz.
- **Gerenciamento de Erros Robusto**: Implementa blocos `try-except` com feedback claro ao usuário para lidar com falhas na transcrição, comunicação com a API Gemini ou síntese de voz, garantindo uma experiência mais estável.

### Como Configurar e Rodar o Projeto (Google Colab)

#### 1. Abrir no Google Colab
Certifique-se de que o notebook esteja aberto em um ambiente Google Colab.

#### 2. Configurar a Chave da API do Gemini
Este projeto utiliza a API do Gemini. Você precisará de uma `GOOGLE_API_KEY`.
- Acesse [Google AI Studio](https://aistudio.google.com/app/apikey) para obter sua chave.
- No Google Colab, vá em `🔑` (ícone de chave no painel esquerdo) -> `Secrets`.
- Adicione um novo secret com o nome `GOOGLE_API_KEY` e cole sua chave API.

#### 3. Execução das Células
Execute todas as células do notebook sequencialmente. As células estão organizadas para:
- Instalar as bibliotecas necessárias.
- Configurar o ambiente e importar módulos.
- Definir os elementos da UI (botões e feedback).
- Inicializar os seletores de modelo e idioma.
- Carregar os modelos Whisper e Gemini iniciais e configurar as funções de callback.
- Definir a função principal de processamento `process_and_respond`.
- Exibir a UI interativa.

#### 4. Interagindo com o Assistente
Após executar todas as células, a interface de usuário será exibida no final do notebook:
- **Iniciar Gravação**: Clique para começar a falar. O status será exibido abaixo.
- **Parar Gravação**: Clique para finalizar sua fala. O sistema transcreverá, processará com Gemini e sintetizará a resposta.
- **Reproduzir Áudio Gravado**: Opcional, para ouvir sua última gravação.
- **Seletores de Modelo e Idioma**: Use os dropdowns para alterar o modelo Whisper, o modelo Gemini e o idioma de interação (por padrão: Português, modelo Whisper 'small', modelo Gemini 'gemini-1.0-pro').

### Testando o Tratamento de Erros
Você pode testar os erros deliberadamente para ver o feedback do sistema:
- **Erro na Transcrição (Whisper)**: Defina a variável global `model = None` em uma célula e tente gravar.
- **Erro na Comunicação com Gemini**: Defina `gemini_model = None` e `chat = None` em uma célula e tente gravar. Ou use uma `GOOGLE_API_KEY` inválida e reinicie o runtime.
- **Erro na Síntese de Voz (gTTS)**: Defina a variável global `language` para um código de idioma inválido (ex: `'xyz'`) e tente gravar.

### Tecnologias Utilizadas
- **Python**
- **Google Colab**
- **Whisper (OpenAI)**: Speech-to-Text
- **Google Generative AI (Gemini)**: Processamento de Linguagem Natural
- **gTTS (Google Text-to-Speech)**: Text-to-Speech
- **ipywidgets**: Componentes de UI interativos
- **JavaScript**: Gravação de áudio no navegador
