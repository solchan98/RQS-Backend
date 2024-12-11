package org.example.quizbox.quiz.application;

import org.example.quizbox.quiz.domain.Answer;

public record CreateAnswer(
        String content,
        boolean correct
) {

    public Answer toAnswer() {
        return new Answer(content, correct);
    }

}