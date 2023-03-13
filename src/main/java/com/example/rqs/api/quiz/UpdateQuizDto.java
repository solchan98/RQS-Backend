package com.example.rqs.api.quiz;

import com.example.rqs.core.quiz.service.dtos.CreateAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateQuizDto {

    private Long quizId;

    private String question;

    private List<CreateAnswer> answers;

    private String type;

    private String hint;
}
