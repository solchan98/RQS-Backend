package org.example.quizbox.game.domain;

import java.util.Optional;

public interface IGameHistoryRepository {

    GameHistory save(GameHistory gameHistory);

    Optional<GameHistory> findBy(GameId gameId);
}
