package br.com.phoenix.hangman.word;

import java.util.Locale;
import java.util.Objects;

public class WordEntry {

    private final String word;
    private final String hint;
    private final String category;
    private final Difficulty difficulty;

    public WordEntry(String word, String hint, String category, Difficulty difficulty) {
        this.word = Objects.requireNonNull(word).toLowerCase(Locale.ROOT);
        this.hint = Objects.requireNonNull(hint);
        this.category = Objects.requireNonNull(category);
        this.difficulty = Objects.requireNonNull(difficulty);
    }

    public String getWord() {
        return word;
    }

    public String getHint() {
        return hint;
    }

    public String getCategory() {
        return category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
