package br.com.phoenix.hangman.fx;

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
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class HangmanFxApp extends Application {

    private final WordService wordService = new WordService(new WordRepository("words.csv"));
    private final GameStatsRepository statsRepository = new GameStatsRepository();
    private final HangmanConsoleRenderer renderer = new HangmanConsoleRenderer();

    private GameStats stats;
    private Difficulty currentDifficulty = Difficulty.MEDIUM;
    private HangmanGame currentGame;
    private WordEntry currentWord;
    private DifficultySettings currentSettings;
    private boolean hintRevealed;
    private boolean silentMode;

    private ComboBox<Difficulty> difficultyCombo;
    private CheckBox silentCheck;
    private Button startButton;
    private Button guessButton;
    private Button hintButton;
    private Button giveUpButton;
    private TextArea boardArea;
    private Label statusLabel;
    private Label statsLabel;

    @Override
    public void start(Stage primaryStage) {
        this.stats = statsRepository.load();

        difficultyCombo = new ComboBox<>(FXCollections.observableArrayList(Difficulty.values()));
        difficultyCombo.setValue(currentDifficulty);

        silentCheck = new CheckBox("Modo silencioso");
        silentCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
            silentMode = newVal;
            updateHintAvailability();
            renderBoard();
        });

        startButton = new Button("Nova partida");
        startButton.setOnAction(e -> startNewGame());

        guessButton = new Button("Informar letra");
        guessButton.setDisable(true);
        guessButton.setOnAction(e -> guessLetter());

        hintButton = new Button("Mostrar dica");
        hintButton.setDisable(true);
        hintButton.setOnAction(e -> showHint());

        giveUpButton = new Button("Desistir");
        giveUpButton.setDisable(true);
        giveUpButton.setOnAction(e -> finishGame(true));

        boardArea = new TextArea("Inicie uma partida para começar a jogar.");
        boardArea.setEditable(false);
        boardArea.setWrapText(false);
        boardArea.setPrefRowCount(18);
        boardArea.setStyle("-fx-font-family: 'Consolas', 'Courier New', monospace;");

        statusLabel = new Label("Pronto para jogar!");
        statsLabel = new Label();
        updateStatsLabel();

        var controlsRow = new HBox(10, new Label("Dificuldade:"), difficultyCombo, startButton, silentCheck);
        controlsRow.setAlignment(Pos.CENTER_LEFT);

        var actionsRow = new HBox(10, guessButton, hintButton, giveUpButton);
        actionsRow.setAlignment(Pos.CENTER_LEFT);

        var root = new VBox(12, controlsRow, boardArea, actionsRow, statusLabel, statsLabel);
        VBox.setVgrow(boardArea, Priority.ALWAYS);
        root.setPadding(new Insets(16));

        var scene = new Scene(root, 760, 540);
        primaryStage.setTitle("Hangman - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startNewGame() {
        currentDifficulty = Optional.ofNullable(difficultyCombo.getValue()).orElse(Difficulty.MEDIUM);
        currentWord = wordService.randomWord(currentDifficulty);
        currentSettings = wordService.settingsFor(currentWord.getDifficulty());
        currentGame = new HangmanGame(currentWord.getWord(), currentSettings.maxAttempts());
        hintRevealed = false;

        guessButton.setDisable(false);
        giveUpButton.setDisable(false);
        updateHintAvailability();

        statusLabel.setText("Status: " + currentGame.getHangmanGameStatus());
        renderBoard();
    }

    private void guessLetter() {
        if (currentGame == null) {
            return;
        }
        var dialog = new TextInputDialog();
        dialog.setTitle("Informar letra");
        dialog.setHeaderText("Digite uma letra");
        dialog.setContentText("Letra:");
        dialog.showAndWait().ifPresent(input -> {
            var trimmed = input.trim();
            if (trimmed.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Entrada inválida", "Informe apenas uma letra.");
                return;
            }
            var character = trimmed.charAt(0);
            try {
                currentGame.inputCharacter(character);
            } catch (LetterAlreadyInputException | IllegalArgumentException ex) {
                showAlert(Alert.AlertType.INFORMATION, "Aviso", ex.getMessage());
            } catch (GameIsFinishedException ex) {
                showAlert(Alert.AlertType.INFORMATION, "Aviso", "O jogo já foi finalizado.");
            }
            renderBoard();
            checkEndGame(false);
        });
    }

    private void showHint() {
        hintRevealed = true;
        hintButton.setDisable(true);
        renderBoard();
    }

    private void checkEndGame(boolean gaveUp) {
        if (currentGame == null) {
            return;
        }
        if (gaveUp || currentGame.getHangmanGameStatus() != HangmanGameStatus.PENDING) {
            finishGame(gaveUp);
        }
    }

    private void finishGame(boolean gaveUp) {
        if (currentGame == null || currentWord == null) {
            return;
        }
        guessButton.setDisable(true);
        hintButton.setDisable(true);
        giveUpButton.setDisable(true);

        String message;
        if (gaveUp) {
            stats.registerLoss();
            message = "Você desistiu da partida.";
        } else if (currentGame.getHangmanGameStatus() == HangmanGameStatus.WIN) {
            stats.registerWin();
            message = silentMode ? "Você venceu." : "Parabéns! Você venceu o jogo!";
        } else {
            stats.registerLoss();
            message = silentMode ? "Você perdeu." : "Que pena! Você foi enforcado.";
        }

        message += "\nPalavra: " + currentWord.getWord();
        if (!silentMode) {
            message += "\n" + stats;
        }

        statsRepository.save(stats);
        updateStatsLabel();
        statusLabel.setText("Status: " + currentGame.getHangmanGameStatus());
        showAlert(Alert.AlertType.INFORMATION, "Fim de jogo", message);
    }

    private void renderBoard() {
        if (currentGame == null || currentWord == null) {
            boardArea.setText("Inicie uma partida para começar a jogar.");
            return;
        }
        var text = renderer.render(currentGame, currentWord.getCategory(), currentWord.getHint(), hintVisible());
        boardArea.setText(text);
        statusLabel.setText("Status: " + currentGame.getHangmanGameStatus());
    }

    private boolean hintVisible() {
        return currentSettings != null && currentSettings.hintsEnabled() && hintRevealed && !silentMode;
    }

    private void updateHintAvailability() {
        if (currentSettings == null) {
            hintButton.setDisable(true);
            return;
        }
        var enabled = currentSettings.hintsEnabled() && !silentMode && !hintRevealed && currentGame != null && currentGame.getHangmanGameStatus() == HangmanGameStatus.PENDING;
        hintButton.setDisable(!enabled);
    }

    private void updateStatsLabel() {
        statsLabel.setText("Placar: " + stats);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        var alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
