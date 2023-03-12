package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.ItemResponse;
import com.example.rqs.core.item.service.dtos.ReadItem;
import com.example.rqs.core.item.service.dtos.ReadItemList;

import java.util.List;

public interface ItemReadService {
    ItemResponse getItem(ReadItem readItem);
    List<ItemResponse> getItems(ReadItemList readItemList);
    List<Long> getItemIds(Long spaceId);
}
