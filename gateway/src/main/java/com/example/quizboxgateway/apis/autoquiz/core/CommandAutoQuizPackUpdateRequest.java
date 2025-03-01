package com.example.quizboxgateway.apis.autoquiz.core;

import lombok.Getter;

import java.util.List;

import static com.example.quizboxgateway.apis.autoquiz.core.QueryAutoQuizPackResponse.*;

@Getter
public class CommandAutoQuizPackUpdateRequest {

    private Long id;
    private String title;
    private List<AutoQuiz> quizzes;
    private List<String> keywords;
}
