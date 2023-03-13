package com.example.rqs.api.cache.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@NoArgsConstructor
public class QuizCache {
    private List<Long> quizIds;
    private int total;
    private String type;
    private String startedAt;

    private QuizCache(List<Long> quizIds, String type) {
        this.quizIds = new ArrayList<>(quizIds);
        Collections.shuffle(this.quizIds);
        this.total = quizIds.size();
        this.type = type;
        this.startedAt = LocalDateTime.now().toString();
    }

    public static QuizCache of(List<Long> quizIds, String type) {
        return new QuizCache(quizIds, type);
    }

    public int quizSize() {
        return quizIds.size();
    }

    public Long pickQuizId() {
        if (quizIds.isEmpty()) {
            return -1L;
        }
        int randomIdx = ThreadLocalRandom.current().nextInt(0, quizIds.size());
        Long pickedId = quizIds.get(randomIdx);
        removeQuizId(pickedId);
        return pickedId;
    }

    public void removeQuizId(Long quizId) {
        quizIds.remove(quizId);
    }
}
