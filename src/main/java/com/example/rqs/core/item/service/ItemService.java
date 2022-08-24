package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.*;

import java.util.List;

public interface ItemService {

    ItemResponse createNewItem(CreateItem createItem);

    ItemResponse getRandomItem(ReadItem readItem);

    List<ItemResponse> getItemList(ReadItem readItem);

    ItemResponse updateItem(UpdateItem updateItem);

    void deleteItem(DeleteItem deleteItem);
}
