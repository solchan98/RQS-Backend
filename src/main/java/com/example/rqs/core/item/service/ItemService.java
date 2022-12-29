package com.example.rqs.core.item.service;

import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.service.dtos.*;
import com.example.rqs.core.member.Member;

import java.util.List;

public interface ItemService {

    ItemResponse createNewItem(CreateItem createItem);

    RandomItemResponse getRandomItem(ReadRandomItem readRandomItem);

    RandomItemResponse getRandomItem(Member member, Long spaceId);

    ItemResponse updateItem(UpdateItem updateItem);

    void deleteItem(DeleteItem deleteItem);

    DeleteItemCacheData getDeleteItemCacheData(Long itemId);


    boolean isItemCreator(Member requester, Item itemId);
    boolean isItemCreator(Member requester, Long itemId);
}
