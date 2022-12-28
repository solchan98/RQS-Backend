package com.example.rqs.core.space.service;

import com.example.rqs.core.space.service.dtos.CreateSpace;
import com.example.rqs.core.space.service.dtos.SpaceResponse;

public interface SpaceRegisterService {
    SpaceResponse createSpace(CreateSpace createSpace);
}
