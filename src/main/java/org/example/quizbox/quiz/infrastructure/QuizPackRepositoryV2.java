package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain2.IQuizPackRepositoryV2;
import org.example.quizbox.quiz.domain2.Quiz;
import org.example.quizbox.quiz.domain2.QuizPack;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizPackRepositoryV2 implements IQuizPackRepositoryV2 {

    @PersistenceContext
    private EntityManager entityManager;

    private final JpaQuizPackRepositoryV2 jpaQuizPackRepository;

    @Override
    public QuizPack save(QuizPack quizPack) {
        try {
            return jpaQuizPackRepository.save(quizPack);
        } catch (DataIntegrityViolationException ex) {
            // TODO UK 제약조건에 따른 상세 핸들링 고민 후 구현하기
            throw new BusinessException(ExceptionConstants.SE1);
        }
    }

    @Override
    public Optional<QuizPack> findById(long quizPackId) {
        return jpaQuizPackRepository.findById(quizPackId);
    }

    @Override
    public Optional<Quiz> findQuizByIdAndQuizId(long quizPackId, long quizId) {
        String jpql = "SELECT q FROM Quiz q WHERE q.quizPack.id = :quizPackId AND q.id = :quizId";
        TypedQuery<Quiz> query = entityManager.createQuery(jpql, Quiz.class);
        query.setParameter("quizPackId", quizPackId);
        query.setParameter("quizId", quizId);

        return Optional.ofNullable(query.getSingleResult());
    }
}
