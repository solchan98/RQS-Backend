package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.IQuizPackRepository;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizPackRepository implements IQuizPackRepository {

    private final EntityManager entityManager;

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

    @Override
    public List<QuizPack> findAllByMemberId(long memberId) {
        String jpql = """
                SELECT qp
                FROM QuizPack qp
                JOIN qp.quizPackMembers.values c
                WHERE c.memberId = :memberId
                """;

        return entityManager.createQuery(jpql, QuizPack.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<QuizPack> findAll() {
        return jpaQuizPackRepository.findAll();
    }
}
