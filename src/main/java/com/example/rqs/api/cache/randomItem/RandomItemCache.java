package com.example.rqs.api.cache.randomItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RandomItemCache {

    private Long totalCnt;

    private List<Long> indexList;
}
