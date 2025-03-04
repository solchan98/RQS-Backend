package com.example.quizboxgateway.apis.autoquiz.task;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class QueryAutoTaskStatusResponse {
    private Long id;
    private Long userId;
    private AutoTaskRequest request;
    private AutoTaskDetailResult detailResult;
    private String taskStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    record AutoTaskRequest(
            String quizPackTitle,
            String content,
            String mimeType,
            Set<String> keywords
    ) {
    }

    record AutoTaskDetailResult(
            QueryAutoQuizPackResponse autoQuizPack,
            String description
    ) {

    }
}
