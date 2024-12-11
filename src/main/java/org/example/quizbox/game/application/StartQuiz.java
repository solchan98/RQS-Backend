package org.example.quizbox.game.application;

import org.example.quizbox.game.domain.QuizPickStrategy;

public record StartQuiz(
        QuizPickStrategy quizPickStrategy,
        long quizPackId,
        long memberId
) {

}
