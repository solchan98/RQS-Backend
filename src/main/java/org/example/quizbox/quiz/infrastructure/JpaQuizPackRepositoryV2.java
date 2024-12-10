package org.example.quizbox.quiz.infrastructure;

import org.example.quizbox.quiz.domain2.QuizPack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuizPackRepositoryV2 extends JpaRepository<QuizPack, Long> {
}
