package br.com.phoenix.hangman.tui;

import br.com.phoenix.hangman.exception.GameIsFinishedException;
import br.com.phoenix.hangman.exception.LetterAlreadyInputException;
import br.com.phoenix.hangman.model.HangmanGame;
import br.com.phoenix.hangman.model.HangmanGameStatus;
import br.com.phoenix.hangman.stats.GameStats;
import br.com.phoenix.hangman.stats.GameStatsRepository;
import br.com.phoenix.hangman.ui.HangmanConsoleRenderer;
import br.com.phoenix.hangman.word.Difficulty;
// import br.com.phoenix.hangman.word.DifficultySettings;
import br.com.phoenix.hangman.word.WordEntry;
import br.com.phoenix.hangman.word.WordRepository;
import br.com.phoenix.hangman.word.WordService;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
// import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
// import java.util.Arrays;
// import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LanternaHangmanApp {

    private final WordService wordService;
    private final GameStatsRepository statsRepository;
    private final HangmanConsoleRenderer renderer;
    private final MultiWindowTextGUI gui;

    private GameStats stats;
    private Difficulty currentDifficulty;
    private boolean silentMode;

    public LanternaHangmanApp(MultiWindowTextGUI gui) {
        this.wordService = new WordService(new WordRepository("words.csv"));
        this.statsRepository = new GameStatsRepository();
        this.renderer = new HangmanConsoleRenderer();
        this.gui = gui;
        this.stats = statsRepository.load();
    }

    public static void main(String[] args) {
        var terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setPreferTerminalEmulator(true);
        terminalFactory.setTerminalEmulatorTitle("Hangman - TUI");
        try (Screen screen = terminalFactory.createScreen()) {
            screen.startScreen();
            var gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
            var app = new LanternaHangmanApp(gui);
            app.start();
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível iniciar a interface TUI", e);
        }
    }

    private void start() {
        this.silentMode = askYesNo("Deseja iniciar no modo silencioso?");
        this.currentDifficulty = chooseDifficulty("Selecione a dificuldade inicial");

        boolean exitRequested = false;
        while (!exitRequested) {
            exitRequested = showMainMenu();
        }
    }

    private boolean showMainMenu() {
        var window = new BasicWindow("Menu Principal");
        var result = new AtomicInteger(0);

        var panel = new Panel(new GridLayout(1));
        panel.addComponent(new Label("Dificuldade atual: " + describeDifficulty(currentDifficulty)));
        panel.addComponent(new Label("Modo silencioso: " + (silentMode ? "Ativo" : "Desativado")));
        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        panel.addComponent(new Button("Nova partida", () -> {
            result.set(1);
            window.close();
        }));
        panel.addComponent(new Button(silentMode ? "Desativar modo silencioso" : "Ativar modo silencioso", () -> {
            silentMode = !silentMode;
            MessageDialog.showMessageDialog(gui, "Modo silencioso",
                    silentMode ? "Modo silencioso ativado." : "Modo silencioso desativado.");
        }));
        panel.addComponent(new Button("Ver placar", () -> MessageDialog.showMessageDialog(gui, "Placar", stats.toString())));
        panel.addComponent(new Button("Trocar dificuldade", () -> {
            currentDifficulty = chooseDifficulty("Selecione a dificuldade");
            MessageDialog.showMessageDialog(gui, "Dificuldade", "Agora jogando em " + describeDifficulty(currentDifficulty));
        }));
        panel.addComponent(new Button("Sair", () -> {
            result.set(9);
            window.close();
        }));

        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        panel.addComponent(new Label("Use TAB/ENTER ou as setas para navegar."));

        window.setComponent(panel);
        gui.addWindowAndWait(window);

        if (result.get() == 1) {
            runGame();
            return false;
        }
        return result.get() == 9;
    }

    private void runGame() {
        var wordEntry = wordService.randomWord(currentDifficulty);
        var settings = wordService.settingsFor(wordEntry.getDifficulty());
        var game = new HangmanGame(wordEntry.getWord(), settings.maxAttempts());
        var state = new GameState(settings.hintsEnabled() && !silentMode);

        var window = new BasicWindow("Jogo - " + wordEntry.getDifficulty().getDisplayName());
        var root = new Panel(new GridLayout(1));

        var boardBox = new TextBox(new TerminalSize(60, 20));
        boardBox.setReadOnly(true);
        root.addComponent(boardBox);

        Runnable updateBoard = () -> boardBox.setText(renderer.render(game,
                wordEntry.getCategory(),
                wordEntry.getHint(),
                state.isHintVisible()));
        updateBoard.run();

        var buttons = new Panel(new GridLayout(2));

        var guessButton = new Button("Informar letra", () -> {
            var input = TextInputDialog.showDialog(gui, "Informar letra", "Digite uma letra", "");
            if (input == null || input.trim().isEmpty()) {
                return;
            }
            var character = input.trim().charAt(0);
            try {
                game.inputCharacter(character);
            } catch (LetterAlreadyInputException | IllegalArgumentException ex) {
                MessageDialog.showMessageDialog(gui, "Atenção", ex.getMessage());
            } catch (GameIsFinishedException ex) {
                MessageDialog.showMessageDialog(gui, "Atenção", "O jogo já foi finalizado.");
            }
            updateBoard.run();
            checkEndGame(window, game, wordEntry, state);
        });
        buttons.addComponent(guessButton);

        var hintButton = new Button("Mostrar dica", () -> {
            state.hintRevealed = true;
            updateBoard.run();
        });
        hintButton.setEnabled(state.hintsAllowed);
        buttons.addComponent(hintButton);

        var giveUpButton = new Button("Desistir", () -> {
            state.gaveUp = true;
            finishGame(window, game, wordEntry, state);
        });
        buttons.addComponent(giveUpButton);

        var closeButton = new Button("Fechar", window::close);
        buttons.addComponent(closeButton);

        root.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        root.addComponent(buttons);

        window.setComponent(root);
        gui.addWindowAndWait(window);
    }

    private void checkEndGame(Window window, HangmanGame game, WordEntry entry, GameState state) {
        if (game.getHangmanGameStatus() != HangmanGameStatus.PENDING) {
            finishGame(window, game, entry, state);
        }
    }

    private void finishGame(Window window, HangmanGame game, WordEntry entry, GameState state) {
        window.close();
        StringBuilder message = new StringBuilder();
        if (state.gaveUp) {
            message.append("Você desistiu da partida.");
            stats.registerLoss();
        } else if (game.getHangmanGameStatus() == HangmanGameStatus.WIN) {
            message.append(silentMode ? "Você venceu." : "Parabéns! Você venceu o jogo!");
            stats.registerWin();
        } else {
            message.append(silentMode ? "Você perdeu." : "Que pena! Você foi enforcado.");
            stats.registerLoss();
        }
        message.append("\nPalavra: ").append(entry.getWord());
        if (!silentMode) {
            message.append("\n").append(stats);
        }
        statsRepository.save(stats);
        MessageDialog.showMessageDialog(gui, "Fim de jogo", message.toString());
    }

    private Difficulty chooseDifficulty(String title) {
        var window = new BasicWindow(title);
        var result = new AtomicReference<Difficulty>();
        var listBox = new ActionListBox(new TerminalSize(50, Difficulty.values().length + 2));
        for (var difficulty : Difficulty.values()) {
            listBox.addItem(describeDifficulty(difficulty), () -> {
                result.set(difficulty);
                window.close();
            });
        }
        window.setComponent(listBox);
        gui.addWindowAndWait(window);
        var selected = result.get();


        if (selected == null) {
            return currentDifficulty != null ? currentDifficulty : Difficulty.MEDIUM;
        }
        return selected;
    }

    private boolean askYesNo(String question) {
        var answer = MessageDialog.showMessageDialog(gui, "Confirmação", question,
                MessageDialogButton.Yes, MessageDialogButton.No);
        return answer == MessageDialogButton.Yes;
    }

    private String describeDifficulty(Difficulty difficulty) {
        var settings = wordService.settingsFor(difficulty);
        var hintText = settings.hintsEnabled() ? "com dicas" : "sem dicas";
        return difficulty.getDisplayName() + " (" + settings.maxAttempts() + " tentativas, " + hintText + ")";
    }

    private static class GameState {
        private final boolean hintsAllowed;
        private boolean hintRevealed;
        private boolean gaveUp;

        private GameState(boolean hintsAllowed) {
            this.hintsAllowed = hintsAllowed;
        }

        private boolean isHintVisible() {
            return hintsAllowed && hintRevealed;
        }
    }
}
