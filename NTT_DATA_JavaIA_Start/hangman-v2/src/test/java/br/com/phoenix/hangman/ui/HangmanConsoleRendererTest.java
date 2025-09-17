package br.com.phoenix.hangman.ui;

import br.com.phoenix.hangman.model.HangmanGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HangmanConsoleRendererTest {

    @Test
    void shouldRenderDeadFaceWhenGameIsLost() {
        var game = new HangmanGame("java", 1);
        game.inputCharacter('x');

        var renderer = new HangmanConsoleRenderer();
        var output = renderer.render(game, "", "", false);

        assertTrue(output.contains("[X]"));
    }
}
