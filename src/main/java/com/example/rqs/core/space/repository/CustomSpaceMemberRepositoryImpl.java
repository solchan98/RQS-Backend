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
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        List<SpaceResponse> spaceResponseList = queryFactory
                .select(Projections.fields(
                        SpaceResponse.class,
                        space.spaceId,
                        space.title,
                        space.visibility,
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
        List<Long> spaceIds = spaceResponseList.stream().map(SpaceResponse::getSpaceId).collect(Collectors.toList());
        Map<Long, List<SpaceMemberResponse>> spaceMemberResponseList = queryFactory
                .from(spaceMember)
                .innerJoin(spaceMember.member, member)
                .innerJoin(spaceMember.space, space).on(space.spaceId.in(spaceIds))
                .transform(groupBy(space.spaceId).as(
                        list(Projections.fields(
                                SpaceMemberResponse.class,
                                spaceMember.spaceMemberId,
                                member.email,
                                member.nickname,
                                spaceMember.joinedAt,
                                spaceMember.role))));
        for (SpaceResponse spaceResponse: spaceResponseList) {
            Long spaceId = spaceResponse.getSpaceId();
            List<SpaceMemberResponse> spaceMembers = spaceMemberResponseList.get(spaceId);
            spaceResponse.setSpaceMemberList(spaceMembers);
        }
        return spaceResponseList;
    }

    private BooleanExpression isVisibility(Boolean isVisibility) {
        if (Objects.isNull(isVisibility)) return null;
        return isVisibility ? space.visibility.isTrue() : space.visibility.isFalse();
    }

    private BooleanExpression lastJoinedAt(LocalDateTime lastJoinedAt) {
        return Objects.isNull(lastJoinedAt) ? null : spaceMember.joinedAt.before(lastJoinedAt);
    }


}
