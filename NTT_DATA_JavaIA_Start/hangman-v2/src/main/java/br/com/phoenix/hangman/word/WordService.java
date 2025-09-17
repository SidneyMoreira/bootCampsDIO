package br.com.phoenix.hangman.word;

import java.security.SecureRandom;
import java.util.List;

public class WordService {

    private final SecureRandom random = new SecureRandom();
    private final WordRepository repository;

    public WordService(WordRepository repository) {
        this.repository = repository;
    }

    public WordEntry randomWord(Difficulty difficulty) {
        List<WordEntry> source = difficulty == null ? repository.findAll() : repository.findByDifficulty(difficulty);
        if (source.isEmpty()) {
            throw new IllegalStateException("Nenhuma palavra disponível para a dificuldade selecionada.");
        }
        var index = random.nextInt(source.size());
        return source.get(index);
    }

    public DifficultySettings settingsFor(Difficulty difficulty) {
        return repository.getDifficultySettings(difficulty);
    }
}
