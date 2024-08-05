package org.example.quizbox.game.presentation;

import org.example.quizbox.game.domain.QuizGameStatus;

public record QuizGameStatusResponse(
        String quizGameId,
        long quizPackId,
        long participantId,
        int totalQuizCount,
        int remainQuizCount,
        int submittedAnswerCount
) {

    public static QuizGameStatusResponse from(QuizGameStatus status) {
        return new QuizGameStatusResponse(
                status.quizGameId().value(),
                status.quizPackId(),
                status.participantId(),
                status.totalQuizCount(),
                status.remainQuizCount(),
                status.submittedAnswerCount()
        );
    }

}
