package com.example.rqs.api.quiz.randomquiz;

import com.example.rqs.api.cache.quiz.QuizCache;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InProgressResponse {
    private final boolean status;
    private final int total;
    private final int left;
    private final LocalDateTime startedAt;

    private InProgressResponse(boolean status, int total, int left, LocalDateTime startedAt) {
        this.status = status;
        this.total = total;
        this.left = left;
        this.startedAt = startedAt;
    }

    public static InProgressResponse from(QuizCache quizCache) {
        return new InProgressResponse(
                true,
                quizCache.getTotal(),
                quizCache.quizSize(),
                LocalDateTime.parse(quizCache.getStartedAt())
        );
    }

    public static InProgressResponse of(boolean status) {
        return new InProgressResponse(false, 0, 0, null);
    }
}
