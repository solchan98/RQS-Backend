package com.example.quizboxgateway.apis.autoquiz.task;

import lombok.Getter;

import java.util.List;

@Getter
public class CommandAddAutoQuizTaskRequest {
    private String quizPackTitle;
    private String base64;
    private String mineType;
    private List<String> previousQuizzes;
}
