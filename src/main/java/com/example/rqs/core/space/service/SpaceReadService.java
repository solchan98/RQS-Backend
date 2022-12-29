package com.example.rqs.core.space.service;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.service.dtos.ReadMembersSpaceList;
import com.example.rqs.core.space.service.dtos.ReadSpace;
import com.example.rqs.core.space.service.dtos.ReadSpaceList;
import com.example.rqs.core.space.service.dtos.SpaceResponse;

import java.util.List;
import java.util.Optional;

public interface SpaceReadService {

    Optional<Space> getSpace(Long spaceId);
    SpaceResponse getSpace(ReadSpace readSpace);
    List<SpaceResponse> getSpaceList(ReadSpaceList readSpaceList);
    List<SpaceResponse> getSpaceList(ReadMembersSpaceList readMembersSpaceList);
}
