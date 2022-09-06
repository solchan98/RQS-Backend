package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.*;
import com.example.rqs.core.member.Member;

import java.util.List;

public interface ItemService {

    ItemResponse createNewItem(CreateItem createItem);

    RandomItemResponse getRandomItem(ReadRandomItem readRandomItem);

    ItemResponse getRandomItem(Member member, Long spaceId);

    ItemResponse getItem(ReadItem readItem);
    List<ItemResponse> getItemList(ReadItemList readItemList);

    ItemResponse updateItem(UpdateItem updateItem);

    void deleteItem(DeleteItem deleteItem);
}
