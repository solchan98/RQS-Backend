package com.example.quizboxgateway.apis.core.games;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QueryInProgressGameResponse {
    private String id;
    private String quizPackTitle;
    private int submittedQuizCount;
    private int quizCount;
    private LocalDateTime lastUpdatedAt;
}
