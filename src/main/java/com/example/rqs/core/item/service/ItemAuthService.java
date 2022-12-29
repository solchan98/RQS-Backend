package com.example.rqs.core.item.service;

import com.example.rqs.core.item.Item;
import com.example.rqs.core.member.Member;

public interface ItemAuthService {
    boolean isItemCreator(Member member, Long itemId);
    boolean isItemCreator(Member member, Item itemId);
}
