package org.example.quizbox.game.application;

import org.example.quizbox.game.domain.Game;

import java.time.LocalDateTime;

public record GameStatusResponse(
        String quizGameId,
        long quizPackId,
        long participantId,
        long totalQuizCount,
        long remainQuizCount,
        long submittedQuizCount,
        GameQuizResponse currentQuiz,
        LocalDateTime lastSubmittedAt
) {

    public static GameStatusResponse from(Game game, GameQuizResponse currentQuiz) {
        return new GameStatusResponse(
                game.getId().value(),
                game.getQuizPackId(),
                game.getCreatorId(),
                game.quizSize(),
                game.remainingQuizSize(),
                game.submittedQuizSize(),
                currentQuiz,
                game.lastSubmittedTime()
        );
    }

}
