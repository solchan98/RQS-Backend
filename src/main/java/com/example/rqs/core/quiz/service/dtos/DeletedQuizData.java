package com.example.rqs.core.quiz.service.dtos;

import lombok.Getter;

@Getter
public class DeletedQuizData {

    private final Long spaceId;

    private final Long quizId;

    private DeletedQuizData(Long spaceId, Long quizId) {
        this.spaceId = spaceId;
        this.quizId = quizId;
    }

    public static DeletedQuizData of(Long spaceId, Long quizId) {
        return new DeletedQuizData(spaceId, quizId);
    }
}
