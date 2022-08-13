package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.ItemResponse;

public interface ItemService {

    ItemResponse createNewItem();

    void updateQuestion();

    void updateAnswer();

    void updateHint();

    void deleteItem();
}
