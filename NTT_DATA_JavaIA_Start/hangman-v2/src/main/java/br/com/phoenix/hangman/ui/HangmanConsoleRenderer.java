package br.com.phoenix.hangman.ui;

import br.com.phoenix.hangman.model.HangmanChar;
import br.com.phoenix.hangman.model.HangmanGame;
import br.com.phoenix.hangman.model.HangmanGameStatus;

import java.util.Collection;
import java.util.stream.Collectors;

public class HangmanConsoleRenderer {

    private static final int ROPE_COLUMN = 8;
    private static final int HEAD_LEFT = ROPE_COLUMN + 1;
    private static final int HEAD_CENTER = ROPE_COLUMN + 2;
    private static final int HEAD_RIGHT = ROPE_COLUMN + 3;

    private static final String[] BASE_BOARD = {
            "  _______     ",
            " |/      |    ",
            " |            ",
            " |            ",
            " |            ",
            " |            ",
            " |            ",
            "_|___         "
    };

    private static final String INFO_INDENT = " ".repeat(countLeadingSpaces(BASE_BOARD[0]));

    public String render(HangmanGame game, String category, String hint, boolean hintVisible) {
        var board = createBoard(game.getCharacters().size());
        drawGallows(board);
        applyBodyParts(board, game);

        var lineSeparator = System.lineSeparator();
        var renderedBoard = new StringBuilder();
        for (var line : board) {
            renderedBoard.append(line).append(lineSeparator);
        }

        renderedBoard.append(lineSeparator);

        renderedBoard
                .append(INFO_INDENT).append("Palavra: ")
                .append(maskedWord(game.getCharacters()))
                .append(lineSeparator)
                .append(INFO_INDENT).append("Categoria: ")
                .append(category)
                .append(lineSeparator)
                .append(INFO_INDENT).append("Tentativas restantes: ")
                .append(game.getRemainingAttempts())
                .append('/')
                .append(game.getMaxAttempts());

        if (!game.getFailAttempts().isEmpty()) {
            renderedBoard
                    .append(" | Letras erradas: ")
                    .append(joinCharacters(game.getFailAttempts()));
        }

        renderedBoard.append(lineSeparator);

        if (hintVisible) {
            renderedBoard
                    .append(INFO_INDENT).append("Dica: ")
                    .append(hint)
                    .append(lineSeparator);
        }

        renderedBoard
                .append(INFO_INDENT).append("Status: ")
                .append(game.getHangmanGameStatus());

        return renderedBoard.toString();
    }

    private void drawGallows(StringBuilder[] board) {
        for (int row = 1; row <= 4; row++) {
            board[row].setCharAt(ROPE_COLUMN, '|');
        }
        board[2].setCharAt(ROPE_COLUMN - 4, '/');
        board[3].setCharAt(ROPE_COLUMN - 5, '/');
        board[4].setCharAt(ROPE_COLUMN - 6, '/');
    }

    private void applyBodyParts(StringBuilder[] board, HangmanGame game) {
        var failAttempts = game.getFailAttempts().size();
        var hanged = game.getHangmanGameStatus() == HangmanGameStatus.LOSE;

        if (failAttempts >= 1) {
            if (hanged) {
                board[2].setCharAt(HEAD_LEFT, '[');
                board[2].setCharAt(HEAD_CENTER, 'X');
                board[2].setCharAt(HEAD_RIGHT, ']');
            } else {
                board[2].setCharAt(HEAD_LEFT, '(');
                board[2].setCharAt(HEAD_CENTER, 'O');
                board[2].setCharAt(HEAD_RIGHT, ')');
            }
        }
        if (failAttempts >= 2) {
            board[3].setCharAt(HEAD_CENTER, '|');
        }
        if (failAttempts >= 3) {
            board[3].setCharAt(HEAD_CENTER - 1, '/');
        }
        if (failAttempts >= 4) {
            board[3].setCharAt(HEAD_CENTER + 1, '\\');
        }
        if (failAttempts >= 5) {
            board[4].setCharAt(HEAD_CENTER - 1, '/');
        }
        if (failAttempts >= 6) {
            board[4].setCharAt(HEAD_CENTER + 1, '\\');
        }
    }

    private StringBuilder[] createBoard(int wordLength) {
        var padding = " ".repeat(Math.max(0, wordLength));
        var board = new StringBuilder[BASE_BOARD.length];
        for (int i = 0; i < BASE_BOARD.length; i++) {
            board[i] = new StringBuilder(BASE_BOARD[i]).append(padding);
        }
        return board;
    }

    private static int countLeadingSpaces(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == ' ') {
            count++;
        }
        return count;
    }

    private String maskedWord(Collection<HangmanChar> characters) {
        var masked = new StringBuilder();
        for (var hangmanChar : characters) {
            if (masked.length() > 0) {
                masked.append(' ');
            }
            masked.append(hangmanChar.isVisible() ? hangmanChar.getCharacter() : '_');
        }
        return masked.toString();
    }

    private String joinCharacters(Collection<Character> characters) {
        return characters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
