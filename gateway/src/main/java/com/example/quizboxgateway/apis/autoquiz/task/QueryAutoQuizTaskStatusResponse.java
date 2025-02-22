package com.example.quizboxgateway.apis.autoquiz.task;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QueryAutoQuizTaskStatusResponse {
    private String quizPackTitle;

    private long taskId;

    private String taskStatus;

    private String taskResult;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
