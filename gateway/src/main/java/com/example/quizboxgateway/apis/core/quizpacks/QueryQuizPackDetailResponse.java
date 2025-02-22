package com.example.quizboxgateway.apis.core.quizpacks;

import lombok.Getter;

import java.util.List;

@Getter
public class QueryQuizPackDetailResponse {

    private long quizPackId;
    private String quizPackTitle;
    private List<QuizPackMember> quizPackMembers;
    private List<Quiz> quizzes;

    @Getter
    public static class Quiz {
        private long quizId;
        private String content;
        private QuizPackMember creator;
        private List<Option> options;
    }

    @Getter
    public static class Option {
        private long optionId;
        private String content;
        private Boolean correct;
    }

    @Getter
    public static class QuizPackMember {
        private long memberId;
        private long quizPackMemberId;
        private String role;
    }
}
