package com.example.rqs.core.item.service;

import com.example.rqs.core.item.service.dtos.RandomItem;
import com.example.rqs.core.item.service.dtos.ReadRandomItem;

public interface ItemRandomService {
    RandomItem getRandomItem(ReadRandomItem readRandomItem);
}
