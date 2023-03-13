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
                        lastItemId(lastItemId)
                )
                .limit(20)
                .orderBy(quiz.quizId.desc())
                .fetch();
    }

    @Override
    public Long countBySpaceId(Long spaceId) {
        return queryFactory
                .select(quiz.count())
                .from(quiz)
                .where(quiz.space.spaceId.eq(spaceId))
                .fetchOne();
    }

    @Override
    public List<Long> getQuizIds(Long spaceId) {
        return queryFactory
                .select(quiz.quizId)
                .from(quiz)
                .where(quiz.space.spaceId.eq(spaceId))
                .fetch();
    }

    private BooleanExpression lastItemId(Long lastItemId) {
        return Objects.isNull(lastItemId) ? null : quiz.quizId.lt(lastItemId);
    }

    private FactoryExpression<QuizResponse> quizResponseConstructorWithoutAnswers() {
        return Projections.constructor(
                QuizResponse.class,
                quiz.quizId,
                quiz.space.spaceId,
                quiz.question,
                quiz.type,
                quiz.spaceMember,
                quiz.hint,
                quiz.createdAt
        );
    }
}
