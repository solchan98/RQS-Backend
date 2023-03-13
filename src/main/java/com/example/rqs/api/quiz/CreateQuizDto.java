package com.example.rqs.api.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateQuizDto {

    private Long spaceId;

    private String question;

    private String answer;

    private String hint;
}
