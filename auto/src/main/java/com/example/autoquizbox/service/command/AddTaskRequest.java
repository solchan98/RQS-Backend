package com.example.autoquizbox.service.command;

import lombok.Getter;

import java.util.List;

@Getter
public class AddTaskRequest {

    private String quizPackTitle;

    private String base64;

    private String mineType; // TODO: base64에서 추출하는 방식으로 수정

    private List<String> previousQuizzes;
}
