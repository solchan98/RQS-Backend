package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.service.dtos.TSpaceResponse;
import com.querydsl.core.types.Projections;
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
                .select(Projections.fields(
                        TSpaceResponse.class,
                        space.spaceId,
                        space.title,
                        space.visibility,
                        item.count().as("itemCount"),
                        spaceMember.count().as("spaceMemberCount"),
                        space.createdAt,
                        space.updatedAt
                        ))
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
        return null;
    }

    @Override
    public List<TSpaceResponse> getMySpaceList(Long memberId, LocalDateTime lastJoinedAt) {
        return null;
    }

    private BooleanExpression lastAt(LocalDateTime lastAt) {
        return Objects.isNull(lastAt) ? null : space.createdAt.before(lastAt);
    }
}
