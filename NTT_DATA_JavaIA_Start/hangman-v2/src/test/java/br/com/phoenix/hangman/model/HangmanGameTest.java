package br.com.phoenix.hangman.model;

import br.com.phoenix.hangman.exception.GameIsFinishedException;
import br.com.phoenix.hangman.exception.LetterAlreadyInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HangmanGameTest {

    @Test
    void shouldRevealLettersWhenGuessIsCorrect() {
        var game = new HangmanGame("java");

        game.inputCharacter('j');

        assertTrue(game.getCharacters().get(0).isVisible());
        assertEquals(HangmanGameStatus.PENDING, game.getHangmanGameStatus());
    }

    @Test
    void shouldHandleAccentedLetters() {
        var game = new HangmanGame("ação");

        game.inputCharacter('a');
        game.inputCharacter('ç');

        assertTrue(game.getCharacters().get(0).isVisible());
        assertTrue(game.getCharacters().get(1).isVisible());
        assertEquals(HangmanGameStatus.PENDING, game.getHangmanGameStatus());
    }

    @Test
    void shouldWinGameWhenAllLettersGuessed() {
        var game = new HangmanGame("ola");

        game.inputCharacter('o');
        game.inputCharacter('l');
        game.inputCharacter('a');

        assertEquals(HangmanGameStatus.WIN, game.getHangmanGameStatus());
    }

    @Test
    void shouldTrackFailedAttemptsAndLoseAfterLimit() {
        var game = new HangmanGame("code");

        game.inputCharacter('a');
        game.inputCharacter('b');
        game.inputCharacter('f');
        game.inputCharacter('g');
        game.inputCharacter('h');
        game.inputCharacter('i');

        assertEquals(0, game.getRemainingAttempts());
        assertEquals(HangmanGameStatus.LOSE, game.getHangmanGameStatus());
    }

    @Test
    void shouldNotAllowRepeatedLetters() {
        var game = new HangmanGame("java");

        game.inputCharacter('j');

        var exception = assertThrows(LetterAlreadyInputException.class, () -> game.inputCharacter('j'));
        assertEquals("A letra 'j' já foi informada anteriormente", exception.getMessage());
    }

    @Test
    void shouldThrowWhenGameAlreadyFinished() {
        var game = new HangmanGame("oi");

        game.inputCharacter('o');
        game.inputCharacter('i');

        assertThrows(GameIsFinishedException.class, () -> game.inputCharacter('a'));
    }

    @Test
    void shouldRejectNonLetterCharacters() {
        var game = new HangmanGame("java");

        var exception = assertThrows(IllegalArgumentException.class, () -> game.inputCharacter('1'));
        assertEquals("Informe apenas letras.", exception.getMessage());
    }

    @Test
    void shouldRespectCustomMaxAttempts() {
        var game = new HangmanGame("teste", 3);

        assertEquals(3, game.getMaxAttempts());

        game.inputCharacter('a');
        game.inputCharacter('b');
        game.inputCharacter('c');

        assertEquals(HangmanGameStatus.LOSE, game.getHangmanGameStatus());
    }
}
