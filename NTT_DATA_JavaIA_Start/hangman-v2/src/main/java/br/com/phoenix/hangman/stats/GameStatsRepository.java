package br.com.phoenix.hangman.stats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
// import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GameStatsRepository {

    private static final String DEFAULT_DIRECTORY = System.getProperty("user.home") + "/.hangman";
    private static final String FILE_NAME = "stats.csv";

    private final Path file;

    public GameStatsRepository() {
        this(Path.of(DEFAULT_DIRECTORY, FILE_NAME));
    }

    public GameStatsRepository(Path file) {
        this.file = file;
    }

    public GameStats load() {
        try {
            ensureDirectory();
            if (Files.notExists(file)) {
                return new GameStats();
            }
            var content = Files.readString(file, StandardCharsets.UTF_8).trim();
            if (content.isEmpty()) {
                return new GameStats();
            }
            var parts = content.split(";");
            if (parts.length < 3) {
                throw new IllegalStateException("Arquivo de estatísticas corrompido: " + file);
            }
            var games = Integer.parseInt(parts[0]);
            var wins = Integer.parseInt(parts[1]);
            var losses = Integer.parseInt(parts[2]);
            return new GameStats(games, wins, losses);
        } catch (IOException ex) {
            throw new IllegalStateException("Não foi possível ler estatísticas", ex);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Valores inválidos no arquivo de estatísticas", ex);
        }
    }

    public void save(GameStats stats) {
        try {
            ensureDirectory();
            var line = stats.getGamesPlayed() + ";" + stats.getWins() + ";" + stats.getLosses();
            Files.writeString(file, line, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalStateException("Não foi possível salvar estatísticas", ex);
        }
    }

    private void ensureDirectory() throws IOException {
        Files.createDirectories(file.getParent());
    }
}
