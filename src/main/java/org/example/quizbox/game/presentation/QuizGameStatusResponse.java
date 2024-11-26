package org.example.quizbox.game.presentation;

import org.example.quizbox.game.domain.QuizGameStatus;

import java.time.LocalDateTime;

public record QuizGameStatusResponse(
        String quizGameId,
        long quizPackId,
        long participantId,
        int totalQuizCount,
        int remainQuizCount,
        int submittedAnswerCount,
        LocalDateTime lastSubmittedAt
) {

    public static QuizGameStatusResponse from(QuizGameStatus status) {
        return new QuizGameStatusResponse(
                status.quizGameId().value(),
                status.quizPackId(),
                status.participantId(),
                status.totalQuizCount(),
                status.remainQuizCount(),
                status.submittedAnswerCount(),
                status.lastSubmittedAt()
        );
    }

}
