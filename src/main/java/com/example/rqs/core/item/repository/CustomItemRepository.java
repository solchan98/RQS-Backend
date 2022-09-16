package com.example.rqs.core.item.repository;

import com.example.rqs.core.item.service.dtos.ItemResponse;

import java.util.List;

public interface CustomItemRepository {

    List<ItemResponse> getItemList(Long spaceId, Long lastItemId);

    Long countBySpaceId(Long spaceId);

    ItemResponse getItem(Long spaceId, int randomIndex);

    ItemResponse getItem(Long itemId);

    List<Long> getItemIdList(Long spaceId);
}
