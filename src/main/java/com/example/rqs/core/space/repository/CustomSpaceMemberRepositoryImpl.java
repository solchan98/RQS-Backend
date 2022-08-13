package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.SpaceMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.rqs.core.space.QSpace.space;
import static com.example.rqs.core.space.QSpaceMember.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CustomSpaceMemberRepositoryImpl implements CustomSpaceMemberRepository {

    private final JPAQueryFactory queryFactory;

    public CustomSpaceMemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<SpaceMember> getSpaceMember(Long memberId, Long spaceId) {
        SpaceMember res = queryFactory
                .selectFrom(spaceMember)
                .where(
                        spaceMember.member.memberId.eq(memberId),
                        spaceMember.space.spaceId.eq(spaceId)
                )
                .join(spaceMember.member)
                .fetchJoin()
                .join(spaceMember.space)
                .fetchJoin()
                .fetchOne();
        return Optional.ofNullable(res);
    }

    @Override
    public List<SpaceMember> getAllSpaceMember(Long memberId, Boolean isVisibility) {
        return queryFactory
                .selectFrom(spaceMember)
                .where(
                        spaceMember.member.memberId.eq(memberId),
                        isVisibility(isVisibility)
                )
                .innerJoin(spaceMember.space, space)
                .fetchJoin()
                .innerJoin(space.spaceMemberList)
                .fetchJoin()
                .fetch();
    }

    private BooleanExpression isVisibility(Boolean isVisibility) {
        if (Objects.isNull(isVisibility)) return null;
        return isVisibility ? space.visibility.isTrue() : space.visibility.isFalse();
    }


}
