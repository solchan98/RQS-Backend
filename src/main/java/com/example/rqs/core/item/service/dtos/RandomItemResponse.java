package com.example.rqs.core.item.service.dtos;

import lombok.Getter;

@Getter
public class RandomItemResponse {

    private final Long selectedCacheListIndex;

    private final Long totalCnt;

    private final ItemResponse itemResponse;

    private RandomItemResponse(Long selectedCacheListIndex, ItemResponse itemResponse, Long totalCnt) {
        this.selectedCacheListIndex = selectedCacheListIndex;
        this.itemResponse = itemResponse;
        this.totalCnt = totalCnt;
    }

    public static RandomItemResponse of(Long selectedCacheListIndex, ItemResponse itemResponse, Long totalCnt) {
        return new RandomItemResponse(selectedCacheListIndex, itemResponse, totalCnt);
    }

    public static RandomItemResponse of(Long selectedCacheListIndex, ItemResponse itemResponse) {
        return new RandomItemResponse(selectedCacheListIndex, itemResponse, null);
    }
}
