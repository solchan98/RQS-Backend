package com.example.rqs.core.quiz.service;

import com.example.rqs.core.quiz.service.dtos.DeletedQuizData;
import com.example.rqs.core.quiz.service.dtos.QuizResponse;
import com.example.rqs.core.quiz.service.dtos.UpdateQuiz;
import com.example.rqs.core.member.Member;

public interface QuizUpdateService {
    QuizResponse updateQuiz(UpdateQuiz updateQuiz);
    DeletedQuizData deleteQuiz(Member member, Long quizId);
}
