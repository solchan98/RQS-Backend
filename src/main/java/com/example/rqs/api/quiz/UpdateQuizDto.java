package com.example.rqs.api.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateQuizDto {

    private Long quizId;

    private String question;

    private String answer;

    private String hint;
}
