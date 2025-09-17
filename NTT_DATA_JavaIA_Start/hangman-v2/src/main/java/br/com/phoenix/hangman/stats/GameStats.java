package br.com.phoenix.hangman.stats;

public class GameStats {

    private int gamesPlayed;
    private int wins;
    private int losses;

    public GameStats() {
    }

    public GameStats(int gamesPlayed, int wins, int losses) {
        if (gamesPlayed < 0 || wins < 0 || losses < 0) {
            throw new IllegalArgumentException("Valores das estatísticas não podem ser negativos");
        }
        if (wins + losses != gamesPlayed) {
            throw new IllegalArgumentException("Inconsistência nos dados das estatísticas");
        }
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.losses = losses;
    }

    public void registerWin() {
        gamesPlayed++;
        wins++;
    }

    public void registerLoss() {
        gamesPlayed++;
        losses++;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getWinRate() {
        if (gamesPlayed == 0) {
            return 0;
        }
        return (int) Math.round((wins * 100.0) / gamesPlayed);
    }

    @Override
    public String toString() {
        return "Partidas: " + gamesPlayed +
                " | Vitórias: " + wins +
                " | Derrotas: " + losses +
                " | Aproveitamento: " + getWinRate() + "%";
    }
}
