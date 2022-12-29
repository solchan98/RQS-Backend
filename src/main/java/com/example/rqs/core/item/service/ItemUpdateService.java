package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.DeletedItemData;
import com.example.rqs.core.item.service.dtos.ItemResponse;
import com.example.rqs.core.item.service.dtos.UpdateItem;
import com.example.rqs.core.member.Member;

public interface ItemUpdateService {
    ItemResponse updateItem(UpdateItem updateItem);
    DeletedItemData deleteItem(Member member, Long itemId);
}
