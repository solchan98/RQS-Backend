package com.example.rqs.core.quiz.service;

import com.example.rqs.core.quiz.service.dtos.CreateQuiz;
import com.example.rqs.core.quiz.service.dtos.QuizResponse;

public interface QuizRegisterService {
    QuizResponse createQuiz(CreateQuiz createQuiz);
}
