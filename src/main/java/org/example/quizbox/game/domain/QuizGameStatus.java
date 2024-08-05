package org.example.quizbox.game.domain;

public record QuizGameStatus(
        QuizGameId quizGameId,
        long quizPackId,
        long participantId,
        int totalQuizCount,
        int remainQuizCount,
        int submittedAnswerCount
) {

}
