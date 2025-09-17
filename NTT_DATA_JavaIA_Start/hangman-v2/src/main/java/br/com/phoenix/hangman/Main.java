package br.com.phoenix.hangman;

import br.com.phoenix.hangman.exception.GameIsFinishedException;
import br.com.phoenix.hangman.exception.LetterAlreadyInputException;
import br.com.phoenix.hangman.model.HangmanGame;
import br.com.phoenix.hangman.model.HangmanGameStatus;
import br.com.phoenix.hangman.stats.GameStats;
import br.com.phoenix.hangman.stats.GameStatsRepository;
import br.com.phoenix.hangman.ui.HangmanConsoleRenderer;
import br.com.phoenix.hangman.word.Difficulty;
import br.com.phoenix.hangman.word.DifficultySettings;
import br.com.phoenix.hangman.word.WordEntry;
import br.com.phoenix.hangman.word.WordRepository;
import br.com.phoenix.hangman.word.WordService;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final HangmanConsoleRenderer RENDERER = new HangmanConsoleRenderer();
    private static final WordService WORD_SERVICE = new WordService(new WordRepository("words.csv"));
    private static final GameStatsRepository STATS_REPOSITORY = new GameStatsRepository();

    public static void main(String... args) {
        var stats = STATS_REPOSITORY.load();
        boolean silentMode = askYesNo("Deseja iniciar no modo silencioso? (s/n): ");

        System.out.println("Bem-vindo ao jogo da forca! Evoluiu com níveis, dicas configuráveis e placar persistente!");

        Optional<WordEntry> customWord = parseCustomWord(args);
        Difficulty currentDifficulty = customWord.map(WordEntry::getDifficulty).orElse(chooseDifficulty());
        Optional<WordEntry> nextWord = customWord;

        boolean keepPlaying = true;
        while (keepPlaying) {
            var wordEntry = nextWord.isPresent() ? nextWord.get() : WORD_SERVICE.randomWord(currentDifficulty);
            var settings = WORD_SERVICE.settingsFor(wordEntry.getDifficulty());
            var hangmanGame = new HangmanGame(wordEntry.getWord(), settings.maxAttempts());
            boolean hintRevealed = false;
            boolean gaveUp = false;
            boolean restartRequested = false;

            while (hangmanGame.getHangmanGameStatus() == HangmanGameStatus.PENDING && !gaveUp) {
                clearConsole();
                printGame(hangmanGame, wordEntry, settings.hintsEnabled() && hintRevealed && !silentMode);
                printMenu(settings, silentMode);
                int option = readGameOption();
                switch (option) {
                    case 1 -> handleCharacterInput(hangmanGame);
                    case 2 -> {
                        if (!settings.hintsEnabled()) {
                            System.out.println("Dicas indisponíveis nesta dificuldade.");
                            waitForEnter();
                        } else if (silentMode) {
                            System.out.println("Desative o modo silencioso para exibir dicas.");
                            waitForEnter();
                        } else {
                            hintRevealed = true;
                        }
                    }
                    case 3 -> gaveUp = true;
                    case 4 -> {
                        if (silentMode) {
                            System.out.println("Modo silencioso ativo. Desative para ver o placar.");
                        } else {
                            showStats(stats);
                        }
                        waitForEnter();
                    }
                    case 5 -> {
                        silentMode = !silentMode;
                        System.out.println(silentMode ? "Modo silencioso ativado." : "Modo silencioso desativado.");
                        waitForEnter();
                    }
                    case 6 -> {
                        currentDifficulty = chooseDifficulty();
                        nextWord = Optional.of(WORD_SERVICE.randomWord(currentDifficulty));
                        restartRequested = true;
                        gaveUp = true;
                    }
                    default -> {
                        System.out.println("Opção inválida, tente novamente.");
                        waitForEnter();
                    }
                }
            }

            if (restartRequested) {
                customWord = Optional.empty();
                continue;
            }

            clearConsole();
            printGame(hangmanGame, wordEntry, settings.hintsEnabled() && hintRevealed && !silentMode);

            if (hangmanGame.getHangmanGameStatus() == HangmanGameStatus.WIN) {
                System.out.println(silentMode ? "Você venceu." : "Parabéns! Você venceu o jogo!");
                stats.registerWin();
            } else if (gaveUp) {
                System.out.println(silentMode ? "Partida encerrada." : "Você desistiu da partida.");
                stats.registerLoss();
            } else {
                System.out.println(silentMode ? "Você perdeu." : "Que pena! Você foi enforcado. Tente novamente.");
                stats.registerLoss();
            }

            System.out.println("A palavra era: " + wordEntry.getWord());
            if (!silentMode) {
                showStats(stats);
            }

            STATS_REPOSITORY.save(stats);

            nextWord = Optional.empty();
            customWord = Optional.empty();

            keepPlaying = askYesNo("Deseja jogar novamente? (s/n): ");
            if (keepPlaying) {
                boolean changeDifficulty = askYesNo("Deseja escolher outra dificuldade? (s/n): ");
                if (changeDifficulty) {
                    currentDifficulty = chooseDifficulty();
                }
            }
        }

        System.out.println("Obrigado por jogar! Até a próxima.");
    }

    private static void printGame(HangmanGame hangmanGame, WordEntry wordEntry, boolean hintVisible) {
        System.out.println(RENDERER.render(hangmanGame, wordEntry.getCategory(), wordEntry.getHint(), hintVisible));
    }

    private static void printMenu(DifficultySettings settings, boolean silentMode) {
        System.out.println();
        System.out.println("Selecione uma das opções:");
        System.out.println("1 - Informar uma letra");
        System.out.println("2 - Mostrar dica" + (settings.hintsEnabled() ? "" : " (indisponível nesta dificuldade)"));
        System.out.println("3 - Desistir da partida");
        System.out.println("4 - Ver placar atual" + (silentMode ? " (modo silencioso)" : ""));
        System.out.println((silentMode ? "5 - Desativar" : "5 - Ativar") + " modo silencioso");
        System.out.println("6 - Trocar dificuldade (reinicia a partida atual)");
        System.out.print("Opção: ");
    }

    private static int readGameOption() {
        var input = SCANNER.nextLine().trim();
        if (input.isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static void handleCharacterInput(HangmanGame hangmanGame) {
        System.out.print("Informe uma letra: ");
        var input = SCANNER.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("Entrada vazia. Informe apenas uma letra.");
            waitForEnter();
            return;
        }
        var character = input.charAt(0);
        try {
            hangmanGame.inputCharacter(character);
        } catch (LetterAlreadyInputException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            waitForEnter();
        } catch (GameIsFinishedException ex) {
            System.out.println("O jogo já foi finalizado.");
            waitForEnter();
        }
    }

    private static Difficulty chooseDifficulty() {
        while (true) {
            System.out.println();
            System.out.println("Escolha a dificuldade:");
            for (var difficulty : Difficulty.values()) {
                var settings = WORD_SERVICE.settingsFor(difficulty);
                var hints = settings.hintsEnabled() ? "com dicas" : "sem dicas";
                System.out.printf("%d - %s (%d tentativas, %s)%n",
                        difficulty.ordinal() + 1,
                        difficulty.getDisplayName(),
                        settings.maxAttempts(),
                        hints);
            }
            System.out.print("Opção: ");
            var input = SCANNER.nextLine().trim();
            try {
                var option = Integer.parseInt(input);
                if (option >= 1 && option <= Difficulty.values().length) {
                    return Difficulty.values()[option - 1];
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Opção inválida, tente novamente.");
        }
    }

    private static boolean askYesNo(String message) {
        System.out.print(message);
        var input = SCANNER.nextLine().trim();
        return input.equalsIgnoreCase("s") || input.equalsIgnoreCase("sim");
    }

    private static void showStats(GameStats stats) {
        System.out.println("Placar: " + stats);
    }

    private static Optional<WordEntry> parseCustomWord(String... args) {
        if (args == null || args.length == 0) {
            return Optional.empty();
        }
        var secretWord = String.join(" ", args).trim();
        if (secretWord.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new WordEntry(secretWord, "Palavra definida via argumento", "Personalizada", Difficulty.MEDIUM));
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void waitForEnter() {
        System.out.print("Pressione ENTER para continuar...");
        SCANNER.nextLine();
    }
}
