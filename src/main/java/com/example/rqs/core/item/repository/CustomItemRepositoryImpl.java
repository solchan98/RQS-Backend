package com.example.rqs.core.item.repository;

import com.example.rqs.core.item.service.dtos.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.rqs.core.item.QItem.*;


import java.util.List;
import java.util.Objects;

public class CustomItemRepositoryImpl implements CustomItemRepository{

    private final JPAQueryFactory queryFactory;

    public CustomItemRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ItemResponse> getItemList(Long spaceId, Long lastItemId) {
        return queryFactory
                .select(
                        Projections.fields(
                                ItemResponse.class,
                                item.itemId,
                                item.space.spaceId,
                                item.question,
                                item.answer,
                                item.hint
                        )
                )
                .from(item)
                .innerJoin(item.space).on(item.space.spaceId.eq(spaceId))
                .where(
                        lastItemId(lastItemId)
                )
                .limit(20)
                .orderBy(item.itemId.desc())
                .fetch();
    }

    @Override
    public Long countBySpaceId(Long spaceId) {
        return queryFactory
                .select(item.count())
                .from(item)
                .where(item.space.spaceId.eq(spaceId))
                .fetchOne();
    }

    @Override
    public ItemResponse getItem(Long spaceId, int randomIndex) {
        return queryFactory
                .select(
                        Projections.fields(
                            ItemResponse.class,
                            item.itemId,
                            item.space.spaceId,
                            item.question,
                            item.answer,
                            item.hint
                ))
                .from(item)
                .where(item.space.spaceId.eq(spaceId))
                .offset(randomIndex)
                .limit(1)
                .fetchOne();
    }

    @Override
    public ItemResponse getItem(Long itemId) {
        return null;
    }

    private BooleanExpression lastItemId(Long lastItemId) {
        return Objects.isNull(lastItemId) ? null : item.itemId.lt(lastItemId);
    }
}
