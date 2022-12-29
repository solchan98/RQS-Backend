package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.service.dtos.SpaceResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.rqs.core.space.QSpace.space;
import static com.example.rqs.core.item.QItem.item;
import static com.example.rqs.core.spacemember.QSpaceMember.spaceMember;

public class CustomSpaceRepositoryImpl implements CustomSpaceRepository {

    private final JPAQueryFactory queryFactory;

    public CustomSpaceRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<SpaceResponse> getSpaceList(LocalDateTime lastCreatedAt) {
        return queryFactory
                .select(getSpaceResponseSelect())
                .from(space)
                .where(
                        space.visibility.isTrue(),
                        spaceCreatedAtBefore(lastCreatedAt))
                .leftJoin(item).on(item.space.spaceId.eq(space.spaceId))
                .leftJoin(spaceMember).on(spaceMember.space.spaceId.eq(space.spaceId))
                .orderBy(space.createdAt.desc())
                .groupBy(space.spaceId)
                .limit(limit)
                .fetch();
    }

    @Override
    public List<SpaceResponse> getSpaceListByTrending(Long offset) {
        return queryFactory
                .select(getSpaceResponseSelect())
                .from(space)
                .where(space.visibility.isTrue())
                .leftJoin(item).on(item.space.spaceId.eq(space.spaceId))
                .leftJoin(spaceMember).on(spaceMember.space.spaceId.eq(space.spaceId))
                .groupBy(space.spaceId)
                .orderBy(spaceMember.countDistinct().desc(), space.createdAt.desc())
                .limit(limit)
                .offset(getOffset(offset))
                .fetch();
    }

    @Override
    public List<SpaceResponse> getMembersSpaceList(Long memberId, Long targetMemberId, LocalDateTime lastJoinedAt) {
        List<SpaceResponse> fetch = queryFactory
                .select(getMySpaceResponseSelect())
                .from(space)
                .leftJoin(spaceMember).on(spaceMember.space.spaceId.eq(space.spaceId))
                .fetchJoin()
                .leftJoin(item).on(item.space.spaceId.eq(space.spaceId))
                .where(
                        spaceMember.member.memberId.eq(targetMemberId),
                        joinedAtBefore(lastJoinedAt),
                        isVisibilityByMemberAndTargetMember(memberId, targetMemberId))
                .orderBy(space.createdAt.desc())
                .groupBy(space.spaceId, spaceMember.role, spaceMember.joinedAt)
                .limit(limit)
                .fetch();

        List<Long> spaceIdList = fetch.stream().map(SpaceResponse::getSpaceId).collect(Collectors.toList());
        List<Long> spaceMemberCountList = queryFactory
                .select(spaceMember.count())
                .from(spaceMember)
                .where(
                        spaceMember.space.spaceId.in(spaceIdList)
                )
                .groupBy(spaceMember.space.spaceId)
                .orderBy(spaceMember.space.createdAt.desc())
                .fetch();

        for (int idx = 0; idx < spaceMemberCountList.size(); idx++) {
            fetch.get(idx).setSpaceMemberCount(spaceMemberCountList.get(idx));
        }

        return fetch;
    }

    private BooleanExpression isVisibilityByMemberAndTargetMember(Long memberId, Long targetMemberId) {
        return Objects.equals(memberId, targetMemberId) ? null : space.visibility.isTrue();
    }


    private BooleanExpression joinedAtBefore(LocalDateTime joinedAt) {
        return Objects.isNull(joinedAt) ? null : spaceMember.joinedAt.before(joinedAt);
    }

    private long getOffset(Long offset) {
        return Objects.isNull(offset) ? 0L : offset;
    }

    private BooleanExpression spaceCreatedAtBefore(LocalDateTime createdAt) {
        return Objects.isNull(createdAt) ? null : space.createdAt.before(createdAt);
    }

    private QBean<SpaceResponse> getSpaceResponseSelect() {
        return Projections.fields(
                SpaceResponse.class,
                space.spaceId,
                space.title,
                space.content,
                space.url.as("imageUrl"),
                space.visibility,
                item.countDistinct().as("itemCount"),
                spaceMember.countDistinct().as("spaceMemberCount"),
                space.createdAt,
                space.updatedAt
        );
    }

    private QBean<SpaceResponse> getMySpaceResponseSelect() {
        return Projections.fields(
                SpaceResponse.class,
                space.spaceId,
                space.title,
                space.visibility,
                space.content,
                space.url.as("imageUrl"),
                item.countDistinct().as("itemCount"),
                spaceMember.countDistinct().as("spaceMemberCount"),
                space.createdAt,
                space.updatedAt,
                spaceMember.role.as("authority"),
                spaceMember.joinedAt.as("memberJoinedAt")
        );
    }
}
