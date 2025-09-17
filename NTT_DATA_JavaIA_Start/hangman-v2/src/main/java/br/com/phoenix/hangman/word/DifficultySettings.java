package br.com.phoenix.hangman.word;

public record DifficultySettings(int maxAttempts, boolean hintsEnabled) {
    public DifficultySettings {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts deve ser positivo");
        }
    }
}
