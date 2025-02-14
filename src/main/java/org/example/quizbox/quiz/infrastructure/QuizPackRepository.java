package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.common.infrastructure.Pagination;
import org.example.quizbox.quiz.domain.IQuizPackRepository;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

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
    public List<QuizPack> findAllBy(long memberId) {
        String jpql = """
                SELECT qp
                FROM QuizPack qp
                    JOIN qp.quizPackMembers.values qpm
                WHERE qpm.memberId = :memberId
                ORDER BY qp.id DESC
                """;
        return entityManager.createQuery(jpql, QuizPack.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<QuizPack> findAllBy(Pagination pageable) {
        if (pageable.lastId() != null) {
            return entityManager.createQuery("""
                            SELECT qp
                            FROM QuizPack qp
                            WHERE qp.id < :lastId
                            ORDER BY qp.id DESC
                            """, QuizPack.class)
                    .setParameter("lastId", pageable.lastId())
                    .setMaxResults(pageable.chunk())
                    .getResultList();
        }

        return entityManager.createQuery("""
                        SELECT qp
                        FROM QuizPack qp
                        ORDER BY qp.id DESC
                        """, QuizPack.class)
                .setMaxResults(pageable.chunk())
                .getResultList();
    }

    @Override
    public List<QuizPack> findAllBy(long memberId, Pagination pageable) {
        if (pageable.lastId() != null) {
            return entityManager.createQuery("""
                            SELECT qp
                            FROM QuizPack qp
                                JOIN qp.quizPackMembers.values qpm
                            WHERE qpm.memberId = :memberId
                                AND qp.id < :lastId
                            ORDER BY qp.id DESC
                            """, QuizPack.class)
                    .setParameter("memberId", memberId)
                    .setParameter("lastId", pageable.lastId())
                    .setMaxResults(pageable.chunk())
                    .getResultList();
        }

        return entityManager.createQuery("""
                        SELECT qp
                        FROM QuizPack qp
                            JOIN qp.quizPackMembers.values qpm
                        WHERE qpm.memberId = :memberId
                        ORDER BY qp.id DESC
                        """, QuizPack.class)
                .setParameter("memberId", memberId)
                .setMaxResults(pageable.chunk())
                .getResultList();
    }

    @Override
    public List<QuizPack> findAll() {
        return jpaQuizPackRepository.findAll();
    }

}
