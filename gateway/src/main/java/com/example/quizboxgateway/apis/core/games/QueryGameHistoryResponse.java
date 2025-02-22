package com.example.quizboxgateway.apis.core.games;

import lombok.Getter;

@Getter
public class QueryGameHistoryResponse {

    private String gameId;

    private long quizPackId;

    private long playerId;

    private int totalQuizSize;

    private int matchQuizSize;
}
