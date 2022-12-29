package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.RandomItemResponse;
import com.example.rqs.core.item.service.dtos.ReadRandomItem;
import com.example.rqs.core.member.Member;

public interface ItemRandomService {
    RandomItemResponse getRandomItem(Member member, Long spaceId);
    RandomItemResponse getRandomItem(ReadRandomItem readRandomItem);
}
