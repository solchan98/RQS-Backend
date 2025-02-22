package com.example.quizboxgateway.apis.core.games;

import lombok.Getter;

import java.util.Set;

@Getter
public class CommandSubmitQuizRequest {
    private Set<Long> optionIds;
}
