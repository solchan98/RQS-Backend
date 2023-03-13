package com.example.rqs.core.quiz.service;

import com.example.rqs.core.quiz.service.dtos.QuizResponse;
import com.example.rqs.core.quiz.service.dtos.ReadQuiz;
import com.example.rqs.core.quiz.service.dtos.ReadQuizzes;

import java.util.List;

public interface QuizReadService {
    QuizResponse getQuiz(ReadQuiz readQuiz);
    List<QuizResponse> getQuizzes(ReadQuizzes readQuizzes);
    List<Long> getQuizIds(Long spaceId);
}
