package br.com.phoenix.hangman;

import br.com.phoenix.hangman.exception.GameIsFinishedException;
import br.com.phoenix.hangman.exception.LetterAlreadyInputException;
import br.com.phoenix.hangman.model.HangmanChar;
import br.com.phoenix.hangman.model.HangmanGame;

import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String... args) {
        var characters = Stream.of(args)
                .map(a -> a.toLowerCase().charAt(0))
                .map(HangmanChar::new).toList();
        System.out.println(characters);
        var hangmanGame = new HangmanGame(characters);
        System.out.println("Bem vindo ao jogo da forca, tente adivinhar a palavra, boa sorte!");
        System.out.println(hangmanGame);

        while (hangmanGame.getHangmanGameStatus() == br.com.phoenix.hangman.model.HangmanGameStatus.PENDING) {
            System.out.println("Selecione uma das opções:");
            System.out.println("1 - Informar uma letra");
            System.out.println("2 - Verificar status do jogo");
            System.out.println("3 - Sair");
            var option = scanner.nextInt();
            switch (option) {
                case 1:
                    inputCharacter(hangmanGame);
                    break;
                case 2:
                    showGameStatus(hangmanGame);
                    break;
                case 3: System.exit(0);
                default: System.out.println("Opção invalida");
            }
        }
    }

    private static void showGameStatus(HangmanGame hangmanGame) {
        System.out.println(hangmanGame.getHangmanGameStatus());
        System.out.println(hangmanGame);
    }

    private static void inputCharacter(HangmanGame hangmanGame) {
        System.out.println("Informe uma letra: ");
        var character = scanner.next().charAt(0);
        try {
            hangmanGame.inputCharacter(character);
        }catch (LetterAlreadyInputException ex) {
            System.out.println(ex.getMessage());
            System.out.println(hangmanGame);
        }catch (GameIsFinishedException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        System.out.println(hangmanGame);
    }
}