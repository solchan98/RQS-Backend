package org.example.quizbox.game.domain;

import java.time.LocalDateTime;

public record QuizGameStatus(
        QuizGameId quizGameId,
        long quizPackId,
        long participantId,
        int totalQuizCount,
        int remainQuizCount,
        int submittedAnswerCount,
        LocalDateTime lastSubmittedAt
) {

}
