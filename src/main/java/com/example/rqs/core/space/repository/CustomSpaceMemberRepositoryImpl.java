package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.SpaceMember;
import com.example.rqs.core.space.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.space.service.dtos.SpaceResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.rqs.core.member.QMember.member;
import static com.example.rqs.core.space.QSpace.space;
import static com.example.rqs.core.space.QSpaceMember.*;

import java.time.LocalDateTime;
import java.util.*;

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
    public List<SpaceResponse> getSpaceResponseList(Long memberId, LocalDateTime lastJoinedAt, Boolean isVisibility) {
        return queryFactory
                .select(Projections.fields(
                        SpaceResponse.class,
                        space.spaceId,
                        space.title,
                        space.visibility,
                        space.itemList.size().as("itemCount"),
                        space.spaceMemberList.size().as("spaceMemberCount"),
                        spaceMember.role.as("authority"),
                        spaceMember.joinedAt.as("memberJoinedAt"),
                        space.createdAt,
                        space.updatedAt))
                .from(spaceMember)
                .where(
                        spaceMember.member.memberId.eq(memberId),
                        lastJoinedAt(lastJoinedAt),
                        isVisibility(isVisibility)
                )
                .leftJoin(spaceMember.space, space)
                .limit(20)
                .orderBy(spaceMember.joinedAt.desc())
                .fetch();
    }

    @Override
    public List<SpaceMemberResponse> getSpaceMemberResponseList(Long spaceId) {
        return queryFactory
                .select(
                        Projections.fields(
                                SpaceMemberResponse.class,
                                spaceMember.spaceMemberId,
                                member.email,
                                member.nickname,
                                spaceMember.joinedAt,
                                spaceMember.role)
                )
                .from(spaceMember)
                .innerJoin(spaceMember.member, member)
                .where(spaceMember.space.spaceId.eq(spaceId))
                .fetch();
    }

    @Override
    public boolean existSpaceMember(Long memberId, Long spaceId) {
        SpaceMember member = queryFactory
                .selectFrom(spaceMember)
                .where(
                        spaceMember.member.memberId.eq(memberId),
                        spaceMember.space.spaceId.eq(spaceId)
                )
                .limit(1)
                .fetchOne();

        return !Objects.isNull(member);
    }

    private BooleanExpression isVisibility(Boolean isVisibility) {
        if (Objects.isNull(isVisibility)) return null;
        return isVisibility ? space.visibility.isTrue() : space.visibility.isFalse();
    }

    private BooleanExpression lastJoinedAt(LocalDateTime lastJoinedAt) {
        return Objects.isNull(lastJoinedAt) ? null : spaceMember.joinedAt.before(lastJoinedAt);
    }


}
