package com.example.rqs.core.item.service.dtos;

import lombok.Getter;

@Getter
public class DeleteItemCacheData {

    private final Long spaceId;

    private final int itemIndex;

    private DeleteItemCacheData(Long spaceId, int itemIndex) {
        this.spaceId = spaceId;
        this.itemIndex = itemIndex;
    }

    public static DeleteItemCacheData of(Long spaceId, int itemIndex) {
        return new DeleteItemCacheData(spaceId, itemIndex);
    }
}
