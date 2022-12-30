package com.example.rqs.api.cache.randomItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RandomItemCache {

    private List<Long> selectableIndexList;

    private int remainingWordCnt;

    private Long remainingExpireTime;

    public RandomItemCache(List<Long> selectableIndexList, long remainingExpireTime) {
        this.selectableIndexList = selectableIndexList;
        this.remainingWordCnt = selectableIndexList.size();
        this.remainingExpireTime = remainingExpireTime;
    }

    public void removeSelectableIndex(int index) {
        this.selectableIndexList.remove(index);
        this.remainingWordCnt = this.selectableIndexList.size();
    }
}
