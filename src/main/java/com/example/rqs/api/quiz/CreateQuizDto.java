package com.example.rqs.api.quiz;

import com.example.rqs.core.quiz.service.dtos.CreateAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateQuizDto {

    private Long spaceId;

    private String question;

    private List<CreateAnswer> createAnswers;

    private String hint;
}
