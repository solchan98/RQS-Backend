package com.example.quizboxgateway.apis.autoquiz.task;

import lombok.Getter;

import java.util.List;

@Getter
public class QueryAutoQuizPackResponse {
    private String title;
    private List<AutoQuiz> quizzes;
    private List<String> keywords;

    @Getter
    public static class AutoQuiz {
        private Long id;
        private String content;
        private String description;
        private List<AutoOption> options;
    }

    @Getter
    public static class AutoOption {
        private Long id;
        private String content;
        private boolean correct;
    }
}
