package com.example.rqs.core.quiz.repository;

import com.example.rqs.core.quiz.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long>, CustomQuizRepository {
}
