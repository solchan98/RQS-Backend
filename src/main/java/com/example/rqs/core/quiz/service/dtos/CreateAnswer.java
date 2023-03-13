package com.example.rqs.core.quiz.service.dtos;

import lombok.Getter;

@Getter
public class CreateAnswer {
    private final String answer;
    private final boolean isCorrect;

    private CreateAnswer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public static CreateAnswer of(String answer, boolean isCorrect) {
        return new CreateAnswer(answer, isCorrect);
    }
}
