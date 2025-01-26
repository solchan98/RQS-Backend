package org.example.quizbox.game.application;

import java.time.LocalDate;
import java.util.Objects;

public class GameHistoriesResponse {
    private final LocalDate localDate;
    private final int countOfGame;
    private final int level;

    public GameHistoriesResponse(
            LocalDate localDate,
            int countOfGame
    ) {
        this.localDate = localDate;
        this.countOfGame = countOfGame;
    }

    public LocalDate localDate() {
        return localDate;
    }

    public int countOfGame() {
        return countOfGame;
    }

    public int level() {
        return level;
    }
}
