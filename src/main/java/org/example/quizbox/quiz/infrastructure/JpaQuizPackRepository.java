package org.example.quizbox.quiz.infrastructure;

import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface JpaQuizPackRepository extends JpaRepository<QuizPack, Long> {

}
