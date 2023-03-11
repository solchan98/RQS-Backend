package com.example.rqs.api.cache.quiz;

import java.util.List;

public interface QuizCacheService {
    Long defaultTTL = 43_200L;
    default String getKey(Long spaceId, Long memberId) {
        return spaceId + "_" + memberId;
    }
    Long pickRandomQuizId(Long spaceId, Long memberId);
    QuizCache start(Long spaceId, Long memberId, List<Long> itemIds);
    void deleteQuiz(Long spaceId, Long quizId);
}
