package com.example.quizboxgateway.apis.autoquiz.task;

import lombok.Getter;

import java.util.List;

@Getter
public class CommandAddAutoQuizTaskRequest {
    private String quizPackTitle;
    private String content;
    private List<String> keywords;
}
