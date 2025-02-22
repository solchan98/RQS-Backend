package com.example.quizboxgateway.apis.core.quizpacks;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QueryQuizPacksResponse {

    private long quizPackId;
    private String quizPackTitle;
    private long memberCount;
    private long quizCount;
    private LocalDateTime createdAt;

    public QueryQuizPacksResponse(
            long quizPackId,
            String quizPackTitle,
            long memberCount,
            long quizCount,
            LocalDateTime createdAt
    ) {
        this.quizPackId = quizPackId;
        this.quizPackTitle = quizPackTitle;
        this.memberCount = memberCount;
        this.quizCount = quizCount;
        this.createdAt = createdAt;
    }
}
