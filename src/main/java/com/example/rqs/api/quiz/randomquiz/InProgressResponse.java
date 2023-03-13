package com.example.rqs.api.quiz.randomquiz;

import com.example.rqs.api.cache.quiz.QuizCache;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InProgressResponse {
    private final boolean status;
    private final int total;
    private final int left;
    private final String type;
    private final LocalDateTime startedAt;

    private InProgressResponse(boolean status, int total, int left, String type, LocalDateTime startedAt) {
        this.status = status;
        this.total = total;
        this.left = left;
        this.type = type;
        this.startedAt = startedAt;
    }

    public static InProgressResponse from(QuizCache quizCache) {
        return new InProgressResponse(
                true,
                quizCache.getTotal(),
                quizCache.quizSize(),
                quizCache.getType(),
                LocalDateTime.parse(quizCache.getStartedAt())
        );
    }

    public static InProgressResponse of(boolean status) {
        return new InProgressResponse(status, 0, 0, "", null);
    }

    public boolean isType(String type) {
        return this.type.equals(type);
    }
}
