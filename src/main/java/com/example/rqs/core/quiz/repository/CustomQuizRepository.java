package com.example.rqs.core.quiz.repository;

import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.quiz.service.dtos.QuizResponse;

import java.util.List;
import java.util.Optional;

public interface CustomQuizRepository {
    List<QuizResponse> getQuizzes(Long spaceId, Long lastItemId);
    Long countBySpaceId(Long spaceId);
    List<Long> getQuizIds(Long spaceId, String type);
    Optional<Quiz> getByChildId(Long childId);
}
