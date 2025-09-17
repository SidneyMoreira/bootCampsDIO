package br.com.phoenix.hangman.word;

public enum Difficulty {
    EASY("Fácil"),
    MEDIUM("Médio"),
    HARD("Difícil");

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Difficulty from(String value) {
        for (var difficulty : values()) {
            if (difficulty.name().equalsIgnoreCase(value)) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Dificuldade desconhecida: " + value);
    }
}
