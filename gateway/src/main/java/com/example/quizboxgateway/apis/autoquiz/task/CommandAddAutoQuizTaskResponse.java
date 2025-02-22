package com.example.quizboxgateway.apis.autoquiz.task;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommandAddAutoQuizTaskResponse {
    private Long id;
    private Long userId;
    private String taskStatus;
    private String taskResult;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
