package br.com.phoenix.hangman.model;

import br.com.phoenix.hangman.exception.GameIsFinishedException;
import br.com.phoenix.hangman.exception.LetterAlreadyInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static br.com.phoenix.hangman.model.HangmanGameStatus.*;

public class HangmanGame {

    private final static int HANGMAN_INITIAL_LINE_LENGTH = 9;

    private final int lineSize;
    private final int hangmanInitialSize;
    private final List<HangmanChar> characters;
    private final List<HangmanChar> hangmanPaths;
    private final List<Character> failAttempts = new ArrayList<>();

    private String hangman;
    private HangmanGameStatus hangmanGameStatus;


    public HangmanGame(final List<HangmanChar> characters) {
        var whiteSpaces = " ".repeat(characters.size());
        var characterSpaces = "-".repeat(characters.size());
        this.lineSize = HANGMAN_INITIAL_LINE_LENGTH + whiteSpaces.length() + System.lineSeparator().length();

        this.hangmanGameStatus = PENDING;
        this.hangmanPaths = buildHangmanPathsPositions();
        buildHangmanDesign(whiteSpaces, characterSpaces);
        this.characters = setCharactersSpacesPositionInGame(characters, whiteSpaces.length());
        this.hangmanInitialSize = hangman.length();
    }

    public HangmanGameStatus getHangmanGameStatus() {
        return hangmanGameStatus;
    }

    public void inputCharacter(final char character) {
        if (this.hangmanGameStatus != PENDING) {
            var message = this.hangmanGameStatus == WIN ?
                    "Parabéns você ganhoou!" :
                    "Você perderu, tente novamente!";
            throw new GameIsFinishedException(message);
        }
        var found = this.characters.stream()
                .filter(c -> c.getCharacter() == character)
                .findFirst();

        if (this.failAttempts.contains(character) || (found.isPresent() && found.get().isVisible())) {
            throw new LetterAlreadyInputException("A letra '" + character + "' já foi informada anteriormente");
        }

        var foundList = this.characters.stream()
                .filter(c -> c.getCharacter() == character)
                .toList();

        if (foundList.isEmpty()) {
            failAttempts.add(character);
            if (failAttempts.size() >= 6) {
                this.hangmanGameStatus = LOSE;
            }
            rebuildHangman(this.hangmanPaths.removeFirst());
            return;
        }

        this.characters.forEach( c-> {
            if (c.getCharacter() == character) {
                c.enableVisibility();
            }
        });

        if (this.characters.stream().noneMatch(HangmanChar::isInvisible)) {
            this.hangmanGameStatus = WIN;
        }
        rebuildHangman(foundList.toArray(HangmanChar[]::new));
    }

    @Override
    public String toString() {
        return this.hangman;
    }

    private List<HangmanChar> setCharactersSpacesPositionInGame(final List<HangmanChar> characters, final int whiteSpacesAmount) {
        final var LINE_LETTER = 6;
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).setPosition(this.lineSize * LINE_LETTER + HANGMAN_INITIAL_LINE_LENGTH + i);
        }
        return characters;
    }

    private List<HangmanChar> buildHangmanPathsPositions() {
        final var HEAD_LINE = 3;
        final var BODY_LINE = 4;
        final var LEGS_LINE = 5;
        return new ArrayList<>(
                List.of(
                        new HangmanChar('O', this.lineSize * HEAD_LINE + 6), //9
                        new HangmanChar('|', this.lineSize * BODY_LINE + 6), //10
                        new HangmanChar('/', this.lineSize * BODY_LINE + 5), //9
                        new HangmanChar('\\', this.lineSize * BODY_LINE + 7), //11
                        new HangmanChar('/', this.lineSize * LEGS_LINE + 5), //10
                        new HangmanChar('\\', this.lineSize * LEGS_LINE + 7) //12
                )
        );
    }

    private void rebuildHangman(final HangmanChar... hangmanChars) {
        var hangmanBuilder = new StringBuilder(this.hangman);
        Stream.of(hangmanChars).forEach(
                h -> hangmanBuilder.setCharAt(h.getPosition(), h.getCharacter()
                ));
        var failMessage = this.failAttempts.isEmpty() ? "" : "Tentativas " + this.failAttempts;
        this.hangman = hangmanBuilder.substring(0, hangmanInitialSize) + failMessage;
    }

    private void buildHangmanDesign(final String whiteSpaces, final String characterSpaces) {
        var sb = new StringBuilder();
        sb.append("  -----  ").append(whiteSpaces).append(System.lineSeparator());
        sb.append("  |   |  ").append(whiteSpaces).append(System.lineSeparator());
        sb.append("  |   |  ").append(whiteSpaces).append(System.lineSeparator());
        sb.append("  |      ").append(whiteSpaces).append(System.lineSeparator());
        sb.append("  |      ").append(whiteSpaces).append(System.lineSeparator());
        sb.append("  |      ").append(whiteSpaces).append(System.lineSeparator());
        sb.append("  |      ").append(whiteSpaces).append(System.lineSeparator());
        sb.append("=========").append(characterSpaces).append(System.lineSeparator());
        this.hangman = sb.toString();
    }
}
