package com.example.rqs.core.item.service.dtos;

import lombok.Getter;

@Getter
public class RandomItem {

    private final Long selectedCacheListIndex;

    private final Long totalCnt;

    private final ItemResponse itemResponse;

    private RandomItem(Long selectedCacheListIndex, ItemResponse itemResponse, Long totalCnt) {
        this.selectedCacheListIndex = selectedCacheListIndex;
        this.itemResponse = itemResponse;
        this.totalCnt = totalCnt;
    }

    public static RandomItem of(Long selectedCacheListIndex, ItemResponse itemResponse, Long totalCnt) {
        return new RandomItem(selectedCacheListIndex, itemResponse, totalCnt);
    }

    public static RandomItem of(Long selectedCacheListIndex, ItemResponse itemResponse) {
        return new RandomItem(selectedCacheListIndex, itemResponse, null);
    }
}
