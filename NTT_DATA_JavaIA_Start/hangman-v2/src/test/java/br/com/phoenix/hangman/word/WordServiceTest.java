package br.com.phoenix.hangman.word;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordServiceTest {

    @Test
    void shouldReturnWordAccordingToDifficulty() {
        var repository = new WordRepository("test_words.csv");
        var service = new WordService(repository);

        var word = service.randomWord(Difficulty.HARD);

        assertEquals(Difficulty.HARD, word.getDifficulty());
        var settings = service.settingsFor(Difficulty.HARD);
        assertEquals(5, settings.maxAttempts());
        assertFalse(settings.hintsEnabled());
    }
}
