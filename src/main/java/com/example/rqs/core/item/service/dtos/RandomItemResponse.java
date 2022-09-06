package com.example.rqs.core.item.service.dtos;

import lombok.Getter;

@Getter
public class RandomItemResponse {

    private final Long selectedCacheListIndex;

    private final ItemResponse itemResponse;

    private RandomItemResponse(Long selectedCacheListIndex, ItemResponse itemResponse) {
        this.selectedCacheListIndex = selectedCacheListIndex;
        this.itemResponse = itemResponse;
    }

    public static RandomItemResponse of(Long selectedCacheListIndex, ItemResponse itemResponse) {
        return new RandomItemResponse(selectedCacheListIndex, itemResponse);
    }
}
