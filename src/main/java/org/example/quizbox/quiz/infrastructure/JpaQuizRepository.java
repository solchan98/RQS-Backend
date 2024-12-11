package org.example.quizbox.quiz.infrastructure;

import org.example.quizbox.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuizRepository extends JpaRepository<Quiz, Long> {
}
