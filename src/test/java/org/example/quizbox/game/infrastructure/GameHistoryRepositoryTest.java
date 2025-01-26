package org.example.quizbox.game.infrastructure;

import org.example.quizbox.game.domain.GameHistory;
import org.example.quizbox.support.infrastructure.DBTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBTest
public class GameHistoryRepositoryTest {

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    @Sql(statements = {
            "INSERT INTO game_history (game_id, quiz_pack_id, player_id, total_quiz_size, match_quiz_size, created_at, created_by, updated_at, updated_by) VALUES " +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_1', 1, 1, 10, 10, '2024-05-01', 1, '2024-05-01', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_2', 1, 1, 10, 10, '2024-06-11', 1, '2024-06-11', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_3', 1, 1, 10, 10, '2024-07-15', 1, '2024-07-15', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_4', 1, 1, 10, 10, '2024-08-08', 1, '2024-08-08', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_5', 1, 1, 10, 10, '2024-09-21', 1, '2024-09-21', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_6', 1, 1, 10, 10, '2024-10-13', 1, '2024-10-13', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_7', 1, 1, 10, 10, '2024-11-11', 1, '2024-11-11', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_8', 1, 1, 10, 10, '2024-11-18', 1, '2024-11-18', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_9', 1, 1, 10, 10, '2024-12-02', 1, '2024-12-02', 1)," +
                    "('findAllByPlayerIdAndBetweenStartDateAndEndDate_10', 1, 1, 10, 10, '2024-12-28', 1, '2024-12-28', 1);"
    })

    @Test
    void findAllByPlayerIdAndBetweenStartDateAndEndDate() {
        List<GameHistory> gameHistories = gameHistoryRepository.findAllBy(
                1L,
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 12, 31)
        );

        assertThat(gameHistories).hasSize(9);
    }
}
