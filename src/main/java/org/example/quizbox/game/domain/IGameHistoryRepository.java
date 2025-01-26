package org.example.quizbox.game.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IGameHistoryRepository {

    GameHistory save(GameHistory gameHistory);

    Optional<GameHistory> findBy(GameId gameId);

    List<GameHistory> findAllBy(long playerId, LocalDate startDate, LocalDate endDate);
}
