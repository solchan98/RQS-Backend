package org.example.quizbox.quiz.application;

import org.example.quizbox.quiz.domain.QuizPack;

public record QuizPackStatus(
        long quizPackId,
        String quizPackTitle,
        long quizCount
) {

    public static QuizPackStatus from(QuizPack quizPack) {
        return new QuizPackStatus(quizPack.getId(), quizPack.getTitle(), quizPack.quizSize());
    }
}
