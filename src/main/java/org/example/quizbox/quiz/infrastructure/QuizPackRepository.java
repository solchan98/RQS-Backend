package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.IQuizPackQueryRepository;
import org.example.quizbox.quiz.domain.IQuizPackRepository;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizPackRepository implements IQuizPackRepository, IQuizPackQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JpaQuizPackRepository jpaQuizPackRepository;

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

    @Transactional(readOnly = true)
    @Override
    public QuizPack getById(long id) {
        QuizPack quizPack = jpaQuizPackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizPack not found"));

        entityManager.detach(quizPack);
        return quizPack;
    }
}
