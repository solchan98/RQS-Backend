package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.quiz.domain.IQuizQueryRepository;
import org.example.quizbox.quiz.domain.Quiz;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizQueryRepository implements IQuizQueryRepository {

    private final EntityManager entityManager;

    private final JpaQuizRepository jpaQuizRepository;

    @Override
    public Quiz getQuizById(long id) {
        Quiz quiz = jpaQuizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        entityManager.detach(quiz);
        return quiz;
    }
}
