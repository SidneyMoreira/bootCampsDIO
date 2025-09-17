package br.com.phoenix.hangman.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WordRepository {

    private final List<WordEntry> words;
    private final Map<Difficulty, DifficultySettings> difficultySettings;

    public WordRepository(String resourcePath) {
        var data = loadWords(resourcePath);
        this.words = data.words();
        this.difficultySettings = data.settings();
    }

    public List<WordEntry> findAll() {
        return List.copyOf(words);
    }

    public List<WordEntry> findByDifficulty(Difficulty difficulty) {
        return words.stream()
                .filter(word -> word.getDifficulty() == difficulty)
                .collect(Collectors.toUnmodifiableList());
    }

    public DifficultySettings getDifficultySettings(Difficulty difficulty) {
        var settings = difficultySettings.get(difficulty);
        if (settings == null) {
            throw new IllegalStateException("Configuração de dificuldade ausente para " + difficulty);
        }
        return settings;
    }

    private WordData loadWords(String resourcePath) {
        var inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalStateException("Arquivo de palavras não encontrado: " + resourcePath);
        }
        var entries = new ArrayList<WordEntry>();
        var settings = new EnumMap<Difficulty, DifficultySettings>(Difficulty.class);
        var seenWords = new HashSet<String>();
        try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            var lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                var parts = line.split(";");
                if (parts.length < 6) {
                    throw new IllegalStateException("Linha inválida no arquivo de palavras (" + lineNumber + "): " + line);
                }
                var word = requireValue(parts[0], "palavra", lineNumber);
                var normalizedWord = word.toLowerCase(Locale.ROOT);
                if (!seenWords.add(normalizedWord)) {
                    throw new IllegalStateException("Palavra duplicada encontrada: " + word + " (linha " + lineNumber + ")");
                }
                var hint = requireValue(parts[1], "dica", lineNumber);
                var category = requireValue(parts[2], "categoria", lineNumber);
                var difficulty = Difficulty.from(requireValue(parts[3], "dificuldade", lineNumber));
                var maxAttempts = parseInt(requireValue(parts[4], "tentativas máximas", lineNumber), lineNumber);
                var hintEnabled = parseBoolean(requireValue(parts[5], "habilitar dicas", lineNumber));

                entries.add(new WordEntry(word, hint, category, difficulty));
                settings.merge(difficulty,
                        new DifficultySettings(maxAttempts, hintEnabled),
                        (existing, incoming) -> {
                            if (existing.maxAttempts() != incoming.maxAttempts() || existing.hintsEnabled() != incoming.hintsEnabled()) {
                                throw new IllegalStateException("Configurações conflitantes para a dificuldade " + difficulty);
                            }
                            return existing;
                        });
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler arquivo de palavras", e);
        }
        if (entries.isEmpty()) {
            throw new IllegalStateException("Nenhuma palavra carregada a partir de " + resourcePath);
        }
        if (settings.size() < Difficulty.values().length) {
            var missing = findMissingDifficulties(settings.keySet());
            throw new IllegalStateException("Dificuldades sem configuração: " + missing);
        }
        return new WordData(List.copyOf(entries), Map.copyOf(settings));
    }

    private List<Difficulty> findMissingDifficulties(Set<Difficulty> configured) {
        var missing = new ArrayList<Difficulty>();
        for (var difficulty : Difficulty.values()) {
            if (!configured.contains(difficulty)) {
                missing.add(difficulty);
            }
        }
        return missing;
    }

    private String requireValue(String value, String fieldName, int lineNumber) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Campo " + fieldName + " obrigatório ausente na linha " + lineNumber);
        }
        return value.trim();
    }

    private int parseInt(String value, int lineNumber) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Valor inválido de inteiro na linha " + lineNumber + ": " + value, ex);
        }
    }

    private boolean parseBoolean(String value) {
        var normalized = value.toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "true", "t", "1", "sim", "s" -> true;
            case "false", "f", "0", "nao", "não", "n" -> false;
            default -> throw new IllegalStateException("Valor booleano inválido: " + value);
        };
    }

    private record WordData(List<WordEntry> words, Map<Difficulty, DifficultySettings> settings) {
    }
}
