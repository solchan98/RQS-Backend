package com.example.rqs.core.quiz.repository;

import com.example.rqs.core.quiz.service.dtos.*;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.rqs.core.quiz.QQuiz.quiz;

import java.util.List;
import java.util.Objects;

public class CustomQuizRepositoryImpl implements CustomQuizRepository {

    private final JPAQueryFactory queryFactory;

    public CustomQuizRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<QuizResponse> getQuizzes(Long spaceId, Long lastItemId) {
        return queryFactory
                .select(quizResponseConstructorWithoutAnswers())
                .from(quiz)
                .innerJoin(quiz.space).on(quiz.space.spaceId.eq(spaceId))
                .where(
                        lastItemId(lastItemId),
                        quiz.isRoot.isTrue()
                )
                .limit(20)
                .orderBy(quiz.quizId.desc())
                .fetch();
    }

    @Override
    //* with tail quizzes */
    public Long countBySpaceId(Long spaceId) {
        return queryFactory
                .select(quiz.count())
                .from(quiz)
                .where(quiz.space.spaceId.eq(spaceId))
                .fetchOne();
    }

    @Override
    //* without tail quizzes */
    public List<Long> getQuizIds(Long spaceId, String type) {
        return queryFactory
                .select(quiz.quizId)
                .from(quiz)
                .where(
                        quiz.space.spaceId.eq(spaceId),
                        quizType(type),
                        quiz.isRoot.isTrue()
                )
                .fetch();
    }

    private BooleanExpression quizType(String type) {
        return Objects.isNull(type) ? null : quiz.type.eq(type);
    }

    private BooleanExpression lastItemId(Long lastItemId) {
        return Objects.isNull(lastItemId) ? null : quiz.quizId.lt(lastItemId);
    }

    private FactoryExpression<QuizResponse> quizResponseConstructorWithoutAnswers() {
        return Projections.constructor(
                QuizResponse.class,
                quiz.quizId,
                quiz.space.spaceId,
                quiz.childId,
                quiz.question,
                quiz.isRoot,
                quiz.type,
                quiz.spaceMember,
                quiz.hint,
                quiz.createdAt
        );
    }
}
