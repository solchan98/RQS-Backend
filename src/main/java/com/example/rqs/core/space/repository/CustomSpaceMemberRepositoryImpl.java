package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.SpaceMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.rqs.core.space.QSpace.space;
import static com.example.rqs.core.space.QSpaceMember.*;

import java.time.LocalDateTime;
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
    public List<SpaceMember> getSpaceMemberList(Long memberId, LocalDateTime lastJoinedAt, Boolean isVisibility) {
        return queryFactory
                .selectFrom(spaceMember)
                .where(
                        spaceMember.member.memberId.eq(memberId),
                        lastJoinedAt(lastJoinedAt),
                        isVisibility(isVisibility)
                )
                .innerJoin(spaceMember.space, space)
                .fetchJoin()
                .innerJoin(space.spaceMemberList)
                .fetchJoin()
                .limit(20)
                .orderBy(spaceMember.joinedAt.desc())
                .fetch();
    }

    private BooleanExpression isVisibility(Boolean isVisibility) {
        if (Objects.isNull(isVisibility)) return null;
        return isVisibility ? space.visibility.isTrue() : space.visibility.isFalse();
    }

    private BooleanExpression lastJoinedAt(LocalDateTime lastJoinedAt) {
        return Objects.isNull(lastJoinedAt) ? null : spaceMember.joinedAt.before(lastJoinedAt);
    }


}
