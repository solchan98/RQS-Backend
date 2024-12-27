package org.example.quizbox.game.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.game.domain.GameHistory;
import org.example.quizbox.game.domain.GameId;
import org.example.quizbox.game.domain.IGameHistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GameHistoryRepository implements IGameHistoryRepository {

    private final JpaGameHistoryRepository jpaGameHistoryRepository;

    @Override
    public GameHistory save(GameHistory gameHistory) {
        return jpaGameHistoryRepository.save(gameHistory);
    }

    @Override
    public Optional<GameHistory> findBy(GameId gameId) {
        return jpaGameHistoryRepository.findById(gameId.value());
    }
}
