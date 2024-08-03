package org.example.quizbox.quiz.presentation;

import org.example.quizbox.quiz.domain.Answer;

public record QuizAnswerResponse(
        long id,
        String content
) {

    public static QuizAnswerResponse from(Answer answer) {
        return new QuizAnswerResponse(answer.getId(), answer.getContent());
    }

}
