# Jogo da Forca (Hangman) em Java

Bem-vindo ao Jogo da Forca! Este é um projeto simples, mas completo, de um jogo da forca desenvolvido em Java, executado via linha de comando. O objetivo é adivinhar uma palavra secreta, letra por letra, antes que o boneco seja completamente desenhado na forca.

## Funcionalidades

- **Interface de Linha de Comando (CLI):** Jogue diretamente no seu terminal.
- **Palavra Customizável:** A palavra a ser adivinhada é passada como argumento na inicialização do programa.
- **Visualização do Jogo:** O estado do jogo, incluindo o boneco na forca e as letras adivinhadas, é exibido a cada jogada.
- **Controle de Tentativas:** O jogador tem 6 tentativas (partes do corpo) antes de perder o jogo.
- **Validação de Entradas:** O jogo valida se uma letra já foi tentada anteriormente.

## Pré-requisitos

Para compilar e executar o projeto, você precisará de:

- **Java Development Kit (JDK):** Versão 17 ou superior.
- **Gradle:** Para gerenciamento de dependências e build do projeto. O projeto já inclui o Gradle Wrapper (`gradlew`), então não é necessário ter o Gradle instalado globalmente.

## Como Executar o Jogo

### Configuração do Gradle (Importante!)

Para que o comando `run` do Gradle funcione corretamente, especialmente para ler a entrada do usuário no terminal, seu arquivo `build.gradle.kts` precisa de duas configurações principais:

1.  **Plugin `application`**: Para criar a tarefa `run`.
2.  **Conexão da Entrada Padrão**: Para permitir que o jogo receba o que você digita.

**Exemplo de `build.gradle.kts`:**
```kotlin
plugins {
    id("java")
    application
}

application {
    // Define a classe de entrada do programa
    mainClass.set("br.com.phoenix.hangman.Main")
}

// Permite que o jogo leia a entrada do terminal
tasks.withType<JavaExec> {
    standardInput = System.`in`
}

// ... outras configurações como repositories, dependencies, etc.
```

1.  **Clone o repositório:**
    ```bash
    git clone <url-do-seu-repositorio>
    cd hangman
    ```

2.  **Compile o projeto com Gradle:**
    Use o Gradle Wrapper para compilar o código.

    No Windows:
    ```cmd
    .\gradlew.bat build
    ```
    No Linux ou macOS:
    ```bash
    ./gradlew build
    ```

3.  **Execute o jogo:**
    Você pode executar o jogo diretamente pela sua IDE (como o IntelliJ IDEA) ou via linha de comando usando o Gradle.

    **A palavra secreta deve ser passada como argumento de programa, com cada letra separada por um espaço.**

    **Exemplo (usando a palavra "teste"):**

    No Windows:
    ```cmd
    .\gradlew.bat run --args="t e s t e"
    ```
    No Linux ou macOS:
    ```bash
    ./gradlew run --args="t e s t e"
    ```

    O jogo começará, e você poderá interagir com ele no terminal.

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

- `br.com.phoenix.hangman`: Contém a classe `Main`, que é o ponto de entrada da aplicação.
- `br.com.phoenix.hangman.model`: Contém as classes que modelam o domínio do jogo, como `HangmanGame`, `HangmanChar` e `HangmanGameStatus`.
- `br.com.phoenix.hangman.exception`: Contém as exceções customizadas para o fluxo do jogo.

## Como Contribuir

Contribuições são bem-vindas! Se você tiver sugestões de melhorias ou encontrar algum bug, sinta-se à vontade para abrir uma *Issue* ou enviar um *Pull Request*.