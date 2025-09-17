package br.com.phoenix.hangman.exception;

public class LetterAlreadyInputException extends RuntimeException {
    public LetterAlreadyInputException(String message) {
        super(message);
    }
}
