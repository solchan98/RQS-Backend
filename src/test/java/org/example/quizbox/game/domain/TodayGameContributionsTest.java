package org.example.quizbox.game.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TodayGameContributionsTest {

    @Test
    void level() {
        TodayGameContributions level0 = new TodayGameContributions(LocalDate.of(2024, 5, 1), 0);
        TodayGameContributions level1 = new TodayGameContributions(LocalDate.of(2024, 5, 1), 1);
        TodayGameContributions level2 = new TodayGameContributions(LocalDate.of(2024, 5, 1), 3);
        TodayGameContributions level3 = new TodayGameContributions(LocalDate.of(2024, 5, 1), 5);
        TodayGameContributions level4 = new TodayGameContributions(LocalDate.of(2024, 5, 1), 7);

        assertAll(
                () -> assertThat(level0.level()).isEqualTo(0),
                () -> assertThat(level1.level()).isEqualTo(1),
                () -> assertThat(level2.level()).isEqualTo(2),
                () -> assertThat(level3.level()).isEqualTo(3),
                () -> assertThat(level4.level()).isEqualTo(4)
        );
    }
}
