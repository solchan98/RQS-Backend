package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.service.dtos.TSpaceResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomSpaceRepository {

    List<TSpaceResponse> getSpaceList(LocalDateTime lastCreatedAt);

    List<TSpaceResponse> getSpaceListByTrending(long offset);

    List<TSpaceResponse> getMySpaceList(Long memberId, LocalDateTime lastJoinedAt);
}
