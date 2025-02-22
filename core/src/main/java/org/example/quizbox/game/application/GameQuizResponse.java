package org.example.quizbox.game.application;

import org.example.quizbox.game.domain.Game;
import org.example.quizbox.quiz.presentation.QuizResponse;

import java.time.LocalDateTime;

public record GameQuizResponse(
        String gameId,
        long quizPackId,
        QuizResponse quiz,
        int submittedQuizCount,
        int totalQuizCount,
        LocalDateTime startedAt,
        LocalDateTime lastSubmittedAt

) {
    public GameQuizResponse(Game game, QuizResponse quizResponse) {
        this(
                game.getId().value(),
                game.getQuizPackId(),
                quizResponse,
                (int) game.submittedQuizSize(),
                (int) game.quizSize(),
                game.getCreatedAt(),
                game.lastSubmittedTime()
        );
    }
}