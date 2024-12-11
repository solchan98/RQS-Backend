package org.example.quizbox.game.domain;

import java.time.LocalDateTime;

public record GameStatus(
        GameId quizGameId,
        long quizPackId,
        long participantId,
        int totalQuizCount,
        int remainQuizCount,
        int submittedQuizCount,
        LocalDateTime lastSubmittedAt
) {

}
