package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.*;

public interface ItemService {

    ItemResponse createNewItem(CreateItem createItem);

    void updateQuestion();

    void updateAnswer();

    void updateHint();

    void deleteItem();
}
