package br.com.phoenix.hangman.model;

import java.text.Normalizer;
import java.util.Objects;

public class HangmanChar {

    private final char character;
    private final char normalizedCharacter;
    private boolean visible;

    public HangmanChar(char character) {
        this.character = character;
        this.normalizedCharacter = normalize(character);
        this.visible = false;
    }

    public char getCharacter() {
        return character;
    }

    public char getNormalizedCharacter() {
        return normalizedCharacter;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isInvisible() {
        return !visible;
    }

    public void reveal() {
        this.visible = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HangmanChar that)) return false;
        return character == that.character &&
                normalizedCharacter == that.normalizedCharacter &&
                visible == that.visible;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, normalizedCharacter, visible);
    }

    @Override
    public String toString() {
        return "HangmanChar{" +
                "character=" + character +
                ", normalizedCharacter=" + normalizedCharacter +
                ", visible=" + visible +
                '}';
    }

    private char normalize(char character) {
        var normalized = Normalizer.normalize(String.valueOf(character), Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "");
        return normalized.toLowerCase().charAt(0);
    }
}
