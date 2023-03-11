package com.example.rqs.api.cache.quiz;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class QuizCache {
    private final List<Long> quizIds;
    private final int total;
    private final LocalDateTime startedAt;

    private QuizCache(List<Long> quizIds) {
        this.quizIds = quizIds;
        this.total = quizIds.size();
        this.startedAt = LocalDateTime.now();
    }

    public static QuizCache of(List<Long> quizIds) {
        return new QuizCache(quizIds);
    }

    public int quizSize() {
        return quizIds.size();
    }

    public Long pickQuizId() {
        if (quizIds.isEmpty()) {
            return -1L;
        }
        int randomIdx = ThreadLocalRandom.current().nextInt(0, quizIds.size());
        return quizIds.get(randomIdx);
    }
}
