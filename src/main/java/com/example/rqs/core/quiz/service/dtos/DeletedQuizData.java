package com.example.rqs.core.quiz.service.dtos;

import lombok.Getter;

@Getter
public class DeletedQuizData {

    private final Long spaceId;

    private final int quizIndex;

    private DeletedQuizData(Long spaceId, int quizIndex) {
        this.spaceId = spaceId;
        this.quizIndex = quizIndex;
    }

    public static DeletedQuizData of(Long spaceId, int quizIndex) {
        return new DeletedQuizData(spaceId, quizIndex);
    }
}
