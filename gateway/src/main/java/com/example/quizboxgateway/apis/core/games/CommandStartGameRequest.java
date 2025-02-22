package com.example.quizboxgateway.apis.core.games;

import lombok.Getter;

@Getter
public class CommandStartGameRequest {
    private String quizPickStrategy;
    private long quizPackId;
}
