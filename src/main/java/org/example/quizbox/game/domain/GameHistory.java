package org.example.quizbox.game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.quizbox.common.domain.Audit;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class GameHistory extends Audit {

    @Id
    private String gameId;

    private long quizPackId;

    private long playerId;

    private int totalQuizSize;

    private int matchQuizSize;

    public GameHistory(GameId gameId, long quizPackId, long playerId, int totalQuizSize, int matchQuizSize) {
        this.gameId = gameId.value();
        this.quizPackId = quizPackId;
        this.playerId = playerId;
        this.totalQuizSize = totalQuizSize;
        this.matchQuizSize = matchQuizSize;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }
}
