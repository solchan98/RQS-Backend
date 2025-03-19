package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.BusinessException;
import org.example.quizbox.quiz.domain.IQuizQueryRepository;
import org.example.quizbox.quiz.domain.Quiz;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.example.quizbox.common.ExceptionConstants.QP6;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizQueryRepository implements IQuizQueryRepository {

    private final EntityManager entityManager;

    private final JpaQuizRepository jpaQuizRepository;

    @Override
    public Quiz getById(long id) {
        return jpaQuizRepository.findById(id)
                .orElseThrow(() -> new BusinessException(QP6));
    }
}
