package com.example.quizboxgateway.apis.core.quizpacks;

import com.example.quizboxgateway.apis.core.keywords.QueryKeywordResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QueryQuizPacksResponse {

    private long quizPackId;
    private String quizPackTitle;
    private long memberCount;
    private long quizCount;
    private List<QueryKeywordResponse> keywords;
    private LocalDateTime createdAt;
}
