package org.example.quizbox.quiz.presentation;

import org.example.quizbox.quiz.domain.QuizPackStatus;

public record QuizPackStatusResponse(
        long quizPackId,
        String quizPackTitle,
        long memberCount,
        long quizCount
) {

    public static QuizPackStatusResponse from(QuizPackStatus status) {
        return new QuizPackStatusResponse(
                status.quizPackId(),
                status.quizPackTitle(),
                status.quizPackMemberCount(),
                status.quizCount()
        );
    }
}
