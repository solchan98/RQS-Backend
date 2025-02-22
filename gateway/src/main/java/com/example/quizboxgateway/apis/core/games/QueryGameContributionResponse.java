package com.example.quizboxgateway.apis.core.games;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class QueryGameContributionResponse {
    private LocalDate localDate;
    private int count;
}
