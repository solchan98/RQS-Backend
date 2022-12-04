package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.service.dtos.SpaceResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomSpaceRepository {

    long limit = 20;

    List<SpaceResponse> getSpaceList(LocalDateTime lastCreatedAt);

    List<SpaceResponse> getSpaceListByTrending(Long offset);

    List<SpaceResponse> getMemberSpaceList(Long memberId, Long targetMemberId, LocalDateTime lastJoinedAt);
}
