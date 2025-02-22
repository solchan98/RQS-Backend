package com.example.quizboxgateway.apis.core.games;

import lombok.Getter;

import java.time.LocalDateTime;

import static com.example.quizboxgateway.apis.core.quizpacks.QueryQuizPackDetailResponse.Quiz;

@Getter
public class QueryGameQuizResponse {
    private String gameId;
    private long quizPackId;
    private Quiz quiz;
    private int submittedQuizCount;
    private int totalQuizCount;
    private LocalDateTime startedAt;
    private LocalDateTime lastSubmittedA;
}
