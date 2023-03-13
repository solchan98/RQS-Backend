package com.example.rqs.core.quiz.service;

import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.member.Member;

public interface QuizAuthService {
    boolean isQuizCreator(Member member, Long itemId);
    boolean isQuizCreator(Member member, Quiz quizId);
}
