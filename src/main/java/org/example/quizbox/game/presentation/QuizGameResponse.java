package org.example.quizbox.game.presentation;

import org.example.quizbox.game.domain.QuizGame;

public record QuizGameResponse(
        String id,
        int remainQuizSize,
        int submittedQuizSize
) {

    public static QuizGameResponse from(QuizGame quizGame) {
        return new QuizGameResponse(
                quizGame.id().value(),
                quizGame.remainQuizSize(),
                quizGame.submittedQuizSize()
        );
    }

}
