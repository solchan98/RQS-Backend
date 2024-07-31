package org.example.quizbox.quiz.infrastructure;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.IQuizPackRepository;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizPackRepository implements IQuizPackRepository {

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
}
