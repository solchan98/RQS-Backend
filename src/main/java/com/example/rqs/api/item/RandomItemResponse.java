package com.example.rqs.api.item;

import com.example.rqs.api.cache.randomItem.RandomItemCache;
import com.example.rqs.core.item.service.dtos.ItemResponse;
import lombok.Getter;

@Getter
public class RandomItemResponse {
    private final int remainingWordCnt;

    private final Long remainingExpireTime;

    private final ItemResponse itemResponse;

    private RandomItemResponse(RandomItemCache randomItemCache, ItemResponse itemResponse) {
        this.remainingWordCnt = randomItemCache.getRemainingWordCnt();
        this.remainingExpireTime = randomItemCache.getRemainingExpireTime();
        this.itemResponse = itemResponse;
    }

    public static RandomItemResponse of (RandomItemCache randomItemCache, ItemResponse itemResponse) {
        return new RandomItemResponse(randomItemCache, itemResponse);
    }
}
