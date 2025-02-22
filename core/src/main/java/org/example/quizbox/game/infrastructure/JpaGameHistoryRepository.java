package org.example.quizbox.game.infrastructure;

import org.example.quizbox.game.domain.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGameHistoryRepository extends JpaRepository<GameHistory, String> {
}
