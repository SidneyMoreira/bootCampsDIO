package br.com.phoenix.hangman.word;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordRepositoryTest {

    @Test
    void shouldLoadWordsFromResource() {
        var repository = new WordRepository("words.csv");

        assertFalse(repository.findAll().isEmpty());
        assertFalse(repository.findByDifficulty(Difficulty.HARD).isEmpty());

        var settings = repository.getDifficultySettings(Difficulty.HARD);
        assertEquals(5, settings.maxAttempts());
        assertFalse(settings.hintsEnabled());
    }
}
