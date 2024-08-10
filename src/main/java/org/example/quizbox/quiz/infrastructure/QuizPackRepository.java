package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.IQuizPackRepository;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizPackRepository implements IQuizPackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JpaQuizPackRepository jpaQuizPackRepository;

    @Override
    public QuizPack save(QuizPack quizPack) {
        try {
            return jpaQuizPackRepository.save(QuizPackEntity.fromDomain(quizPack)).toDomain();
        } catch (DataIntegrityViolationException ex) {
            // TODO UK 제약조건에 따른 상세 핸들링 고민 후 구현하기
            throw new BusinessException(ExceptionConstants.SE1);
        }
    }

    @Override
    public Optional<QuizPack> findById(long quizPackId) {
        return jpaQuizPackRepository.findById(quizPackId).map(QuizPackEntity::toDomain);
    }

    @Override
    public Optional<Quiz> findQuizByIdAndQuizId(long quizPackId, long quizId) {
        String jpql = "SELECT q FROM QuizEntity q WHERE q.quizPackEntity.id = :quizPackId AND q.id = :quizId";
        TypedQuery<QuizEntity> query = entityManager.createQuery(jpql, QuizEntity.class);
        query.setParameter("quizPackId", quizPackId);
        query.setParameter("quizId", quizId);

        return query.getResultStream().findFirst().map(QuizEntity::toDomain);
    }

    @Override
    public Optional<QuizPackMember> findQuizPackMemberByIdAndMemberId(long quizPackId, long memberId) {
        String jpql = "SELECT qpm FROM QuizPackMemberEntity qpm WHERE qpm.quizPackEntity.id = :quizPackId AND qpm.memberId = :memberId";
        TypedQuery<QuizPackMemberEntity> query = entityManager.createQuery(jpql, QuizPackMemberEntity.class);
        query.setParameter("quizPackId", quizPackId);
        query.setParameter("memberId", memberId);

        return query.getResultStream().findFirst().map(QuizPackMemberEntity::toDomain);
    }
}
