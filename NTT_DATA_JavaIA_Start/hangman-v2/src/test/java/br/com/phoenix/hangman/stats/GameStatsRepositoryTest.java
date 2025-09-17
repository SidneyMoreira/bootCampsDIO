package br.com.phoenix.hangman.stats;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class GameStatsRepositoryTest {

    @Test
    void shouldPersistAndLoadStatistics() throws IOException {
        var tempFile = Files.createTempFile("hangman-stats", ".csv");
        var repository = new GameStatsRepository(tempFile);
        var stats = new GameStats();

        stats.registerWin();
        stats.registerLoss();

        repository.save(stats);
        var loaded = repository.load();

        assertEquals(2, loaded.getGamesPlayed());
        assertEquals(1, loaded.getWins());
        assertEquals(1, loaded.getLosses());
        assertEquals(50, loaded.getWinRate());
    }
}
