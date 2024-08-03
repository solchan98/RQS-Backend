package org.example.quizbox.quiz.presentation;

import org.example.quizbox.quiz.domain.QuizPack;

public record QuizPackStatusResponse(
        long quizPackId,
        String quizPackTitle,
        long quizCount,
        long memberCount
) {

    public static QuizPackStatusResponse from(QuizPack quizPack) {
        return new QuizPackStatusResponse(quizPack.getId(), quizPack.getTitle(), quizPack.quizSize(), quizPack.memberSize());
    }
}
