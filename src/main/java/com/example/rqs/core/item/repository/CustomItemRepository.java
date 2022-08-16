package com.example.rqs.core.item.repository;

import com.example.rqs.core.item.service.dtos.ItemResponse;

import java.util.List;

public interface CustomItemRepository {

    List<ItemResponse> getItemList(Long spaceId, Long lastItemId);
}