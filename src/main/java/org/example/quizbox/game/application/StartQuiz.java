package org.example.quizbox.game.application;

public record StartQuiz(
        QuizPickStrategy quizPickStrategy,
        long quizPackId,
        long memberId
) {

}
