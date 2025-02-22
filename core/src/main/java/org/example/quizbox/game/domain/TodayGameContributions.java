package org.example.quizbox.game.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Collection;

@Getter
public class TodayGameContributions {
    private final LocalDate localDate;

    private final int count;

    public TodayGameContributions(LocalDate localDate, int count) {
        this.localDate = localDate;
        this.count = count;
    }

    public TodayGameContributions(LocalDate localDate, Collection<GameHistory> gameHistories) {
        this.localDate = localDate;
        this.count = gameHistories.size();
    }

    public int level() {
        if (count >= 7) {
            return 4;
        }

        if (count >= 5) {
            return 3;
        }

        if (count >= 3) {
            return 2;
        }

        if (count >= 1) {
            return 1;
        }

        return 0;
    }
}
