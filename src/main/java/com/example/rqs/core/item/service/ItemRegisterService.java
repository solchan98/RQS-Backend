package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.CreateItem;
import com.example.rqs.core.item.service.dtos.ItemResponse;

public interface ItemRegisterService {
    ItemResponse createItem(CreateItem createItem);
}
