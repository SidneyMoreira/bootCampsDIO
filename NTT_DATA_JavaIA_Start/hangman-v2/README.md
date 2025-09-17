# Jogo da Forca (Hangman) em Java

Bem-vindo ao Jogo da Forca! Agora o projeto conta com níveis configuráveis, dicionário externo, placar persistente e três interfaces diferentes.

## Funcionalidades

- **Interface de Linha de Comando (CLI):** jogo totalmente no terminal, com limpeza de tela a cada jogada.
- **Interface TUI (Lanterna):** modo alternativo com menus e janelas utilizando a biblioteca Lanterna.
- **Interface JavaFX:** aplicação gráfica com botões e painel ASCII renderizado dentro de uma janela.
- **Dicionário configurável:** palavras, dicas, categorias, limite de tentativas e uso de dicas definidos em `src/main/resources/words.csv`.
- **Níveis de dificuldade:** EASY, MEDIUM e HARD, cada um com regras vindas do CSV; é possível alternar durante o jogo.
- **Suporte a acentos:** entradas com letras acentuadas e cedilha são normalizadas automaticamente.
- **Modo silencioso opcional:** oculta dicas e mensagens de placar durante a sessão.
- **Placar persistente:** vitórias/derrotas gravadas em `%USERPROFILE%\.hangman\stats.csv` (ou diretório equivalente no Linux/macOS).
- **Testes unitários:** cobertura para regras do jogo, carregamento do dicionário, persistência de estatísticas e renderização.

## Pré-requisitos

- **Java Development Kit (JDK)** 17 ou superior.
- **Gradle** (opcional). O projeto inclui o Gradle Wrapper (`gradlew`/`gradlew.bat`).

> No Windows, execute `chcp 65001` antes de rodar o jogo para garantir a exibição correta dos acentos.

## Como executar

1. Clone o repositório e acesse a pasta do projeto:
   ```bash
   git clone <url-do-seu-repositorio>
   cd hangman
   ```
2. Escolha a interface desejada e execute com o Gradle Wrapper:
   - Windows (CLI):
     ```cmd
     .\gradlew.bat runCli
     ```
   - Windows (TUI Lanterna):
     ```cmd
     .\gradlew.bat runLanterna
     ```
   - Windows (JavaFX):
     ```cmd
     .\gradlew.bat run
     ```
   - Linux/macOS (CLI):
     ```bash
     ./gradlew runCli
     ```
   - Linux/macOS (TUI Lanterna):
     ```bash
     ./gradlew runLanterna
     ```
   - Linux/macOS (JavaFX):
     ```bash
     ./gradlew run
     ```

### Executando sem o wrapper

Se preferir usar Gradle instalado globalmente:
```bash
gradle run
```

Ou compile manualmente com `javac` e execute com `java` informando o classpath de `build/classes/java/main`.

## Estrutura do projeto

- `br.com.phoenix.hangman` – ponto de entrada (CLI) em `Main`.
- `br.com.phoenix.hangman.tui` – ponto de entrada TUI (`LanternaHangmanApp`).
- `br.com.phoenix.hangman.fx` – ponto de entrada JavaFX (`HangmanFxApp`).
- `br.com.phoenix.hangman.model` – regras do jogo (`HangmanGame`, `HangmanChar`, `HangmanGameStatus`).
- `br.com.phoenix.hangman.ui` – renderização ASCII e lógica de exibição compartilhada.
- `br.com.phoenix.hangman.word` – carregamento/serviço de palavras e configurações de dificuldade.
- `br.com.phoenix.hangman.stats` – gerenciamento e persistência do placar.
- `src/main/resources/words.csv` – banco de palavras, dicas e regras.

## Executando testes

Use o wrapper para rodar a suíte de testes JUnit:
```bash
./gradlew test       # Linux/macOS
.\gradlew.bat test  # Windows
```

## Como contribuir

Sugestões e melhorias são bem-vindas! Abra uma issue ou envie um pull request sempre que encontrar problemas ou tiver novas ideias para o jogo.
