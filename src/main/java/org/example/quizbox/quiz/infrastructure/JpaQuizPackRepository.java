package org.example.quizbox.quiz.infrastructure;

import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuizPackRepository extends JpaRepository<QuizPack, Long> {
}
