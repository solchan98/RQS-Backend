package org.example.quizbox.game.infrastructure;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.game.domain.GameHistory;
import org.example.quizbox.game.domain.GameId;
import org.example.quizbox.game.domain.IGameHistoryRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GameHistoryRepository implements IGameHistoryRepository {

    private final EntityManager entityManager;

    private final JpaGameHistoryRepository jpaGameHistoryRepository;

    @Override
    public GameHistory save(GameHistory gameHistory) {
        return jpaGameHistoryRepository.save(gameHistory);
    }

    @Override
    public Optional<GameHistory> findBy(GameId gameId) {
        return jpaGameHistoryRepository.findById(gameId.value());
    }

    @Override
    public List<GameHistory> findAllBy(long playerId, LocalDate startDate, LocalDate endDate) {
        String jpql = """
                SELECT gh
                FROM GameHistory gh
                WHERE gh.playerId = :playerId
                    AND gh.createdAt >= :startDate
                    AND gh.createdAt <= :endDate
                """;
        return entityManager.createQuery(jpql, GameHistory.class)
                .setParameter("playerId", playerId)
                .setParameter("startDate", startDate.atStartOfDay())
                .setParameter("endDate", endDate.atTime(23, 59))
                .getResultList();
    }
}
