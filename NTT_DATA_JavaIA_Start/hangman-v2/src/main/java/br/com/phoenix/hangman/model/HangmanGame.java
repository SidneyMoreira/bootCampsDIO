package br.com.phoenix.hangman.model;

import br.com.phoenix.hangman.exception.GameIsFinishedException;
import br.com.phoenix.hangman.exception.LetterAlreadyInputException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static br.com.phoenix.hangman.model.HangmanGameStatus.LOSE;
import static br.com.phoenix.hangman.model.HangmanGameStatus.PENDING;
import static br.com.phoenix.hangman.model.HangmanGameStatus.WIN;

public class HangmanGame {

    private final int maxFailAttempts;
    private final List<HangmanChar> characters;
    private final Set<Character> failAttempts = new LinkedHashSet<>();
    private final Set<Character> successfulAttempts = new LinkedHashSet<>();

    private HangmanGameStatus hangmanGameStatus = PENDING;

    public HangmanGame(final String secretWord) {
        this(secretWord, 6);
    }

    public HangmanGame(final String secretWord, final int maxFailAttempts) {
        if (secretWord == null || secretWord.isBlank()) {
            throw new IllegalArgumentException("A palavra secreta não pode ser vazia.");
        }
        if (maxFailAttempts < 1) {
            throw new IllegalArgumentException("O número máximo de erros deve ser positivo.");
        }
        this.maxFailAttempts = maxFailAttempts;
        this.characters = new ArrayList<>(secretWord.length());
        for (int i = 0; i < secretWord.length(); i++) {
            var character = secretWord.charAt(i);
            var hangmanChar = new HangmanChar(character);
            if (!Character.isLetter(character)) {
                hangmanChar.reveal();
            }
            this.characters.add(hangmanChar);
        }
    }

    public HangmanGameStatus getHangmanGameStatus() {
        return hangmanGameStatus;
    }

    public int getMaxAttempts() {
        return maxFailAttempts;
    }

    public int getRemainingAttempts() {
        return maxFailAttempts - failAttempts.size();
    }

    public Set<Character> getFailAttempts() {
        return Collections.unmodifiableSet(failAttempts);
    }

    public Set<Character> getSuccessfulAttempts() {
        return Collections.unmodifiableSet(successfulAttempts);
    }

    public List<HangmanChar> getCharacters() {
        return List.copyOf(characters);
    }

    public void inputCharacter(final char rawCharacter) {
        if (this.hangmanGameStatus != PENDING) {
            throw new GameIsFinishedException("Game already finished");
        }

        var normalizedCharacter = normalize(rawCharacter);
        if (!Character.isLetter(rawCharacter)) {
            throw new IllegalArgumentException("Informe apenas letras.");
        }

        if (successfulAttempts.contains(normalizedCharacter) || failAttempts.contains(normalizedCharacter)) {
            throw new LetterAlreadyInputException("A letra '" + normalizedCharacter + "' já foi informada anteriormente");
        }

        var found = false;
        for (var hangmanChar : this.characters) {
            if (hangmanChar.getNormalizedCharacter() == normalizedCharacter) {
                hangmanChar.reveal();
                found = true;
            }
        }

        if (found) {
            successfulAttempts.add(normalizedCharacter);
            if (this.characters.stream().noneMatch(HangmanChar::isInvisible)) {
                this.hangmanGameStatus = WIN;
            }
            return;
        }

        failAttempts.add(normalizedCharacter);
        if (failAttempts.size() >= maxFailAttempts) {
            this.hangmanGameStatus = LOSE;
        }
    }

    private char normalize(char value) {
        var normalized = Normalizer.normalize(String.valueOf(value), Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "");
        return normalized.toLowerCase(Locale.ROOT).charAt(0);
    }
}
