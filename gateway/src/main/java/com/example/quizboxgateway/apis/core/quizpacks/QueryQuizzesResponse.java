package com.example.quizboxgateway.apis.core.quizpacks;

import lombok.Getter;

import java.util.List;

import static com.example.quizboxgateway.apis.core.quizpacks.QueryQuizPackDetailResponse.Option;
import static com.example.quizboxgateway.apis.core.quizpacks.QueryQuizPackDetailResponse.QuizPackMember;

@Getter
public class QueryQuizzesResponse {
    private long quizId;
    private String content;
    private QuizPackMember creator;
    private List<Option> options;
}
