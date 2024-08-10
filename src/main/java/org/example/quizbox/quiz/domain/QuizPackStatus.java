package org.example.quizbox.quiz.domain;

public record QuizPackStatus(
        long quizPackId,
        String quizPackTitle,
        long quizPackMemberCount,
        long quizCount
) {

}
