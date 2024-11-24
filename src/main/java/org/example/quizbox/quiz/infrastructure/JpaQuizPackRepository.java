package org.example.quizbox.quiz.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuizPackRepository extends JpaRepository<QuizPackEntity, Long> {
}
