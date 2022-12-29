package com.example.rqs.core.item.service.dtos;

import lombok.Getter;

@Getter
public class DeletedItemData {

    private final Long spaceId;

    private final int itemIndex;

    private DeletedItemData(Long spaceId, int itemIndex) {
        this.spaceId = spaceId;
        this.itemIndex = itemIndex;
    }

    public static DeletedItemData of(Long spaceId, int itemIndex) {
        return new DeletedItemData(spaceId, itemIndex);
    }
}
