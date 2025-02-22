package com.example.quizboxgateway.apis.core.games;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommandStartGameResponse {
    private String quizGameId;
    private long quizPackId;
    private long participantId;
    private long totalQuizCount;
    private long remainQuizCount;
    private long submittedQuizCount;
    private QueryGameQuizResponse currentQuiz;
    private LocalDateTime lastSubmittedAt;
}
