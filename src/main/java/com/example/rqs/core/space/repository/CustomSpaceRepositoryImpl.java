package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.service.dtos.TSpaceResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.rqs.core.space.QSpace.space;
import static com.example.rqs.core.item.QItem.item;
import static com.example.rqs.core.space.QSpaceMember.spaceMember;

public class CustomSpaceRepositoryImpl implements CustomSpaceRepository{

    private final JPAQueryFactory queryFactory;

    public CustomSpaceRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<TSpaceResponse> getSpaceList(LocalDateTime lastCreatedAt) {
        return queryFactory
                .select(getSpaceResponseSelect())
                .from(space)
                .where(
                        space.visibility.isTrue(),
                        lastAt(lastCreatedAt))
                .leftJoin(item).on(item.space.spaceId.eq(space.spaceId))
                .leftJoin(spaceMember).on(spaceMember.space.spaceId.eq(space.spaceId))
                .orderBy(space.createdAt.desc())
                .groupBy(space.spaceId)
                .fetch();
    }

    @Override
    public List<TSpaceResponse> getSpaceListByTrending(long offset) {
        return queryFactory
                .select(getSpaceResponseSelect())
                .from(space)
                .where(space.visibility.isTrue())
                .leftJoin(item).on(item.space.spaceId.eq(space.spaceId))
                .leftJoin(spaceMember).on(spaceMember.space.spaceId.eq(space.spaceId))
                .groupBy(space.spaceId)
                .orderBy(spaceMember.count().desc(), space.createdAt.desc())
                .limit(limit)
                .offset(offset)
                .fetch();
    }

    @Override
    public List<TSpaceResponse> getMySpaceList(Long memberId, LocalDateTime lastJoinedAt) {
        return null;
    }

    private BooleanExpression lastAt(LocalDateTime lastAt) {
        return Objects.isNull(lastAt) ? null : space.createdAt.before(lastAt);
    }

    private QBean<TSpaceResponse> getSpaceResponseSelect() {
        return Projections.fields(
                TSpaceResponse.class,
                space.spaceId,
                space.title,
                space.visibility,
                item.count().as("itemCount"),
                spaceMember.count().as("spaceMemberCount"),
                space.createdAt,
                space.updatedAt
        );
    }
}
