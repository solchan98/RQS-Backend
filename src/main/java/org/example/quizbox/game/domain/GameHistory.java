package org.example.quizbox.game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class GameHistory {

    @Id
    private String gameid;

    private long quizPackId;

    private long playerId;

    private int totalQuizSize;

    private int matchQuizSize;

    public GameHistory(GameId gameid, long quizPackId, long playerId, int totalQuizSize, int matchQuizSize) {
        this.gameid = gameid.value();
        this.quizPackId = quizPackId;
        this.playerId = playerId;
        this.totalQuizSize = totalQuizSize;
        this.matchQuizSize = matchQuizSize;
    }
}
