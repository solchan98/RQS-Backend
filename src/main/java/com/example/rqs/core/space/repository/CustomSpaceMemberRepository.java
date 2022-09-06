package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.SpaceMember;
import com.example.rqs.core.space.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.space.service.dtos.SpaceResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CustomSpaceMemberRepository {

    Optional<SpaceMember> getSpaceMember(Long memberId, Long spaceId);

    List<SpaceResponse> getSpaceResponseList(Long memberId, LocalDateTime lastJoinedAt, Boolean isVisibility);

    List<SpaceMemberResponse> getSpaceMemberResponseList(Long spaceId);

    boolean existSpaceMember(Long memberId, Long spaceId);

    boolean existSpaceMember(Long spaceMemberId);
}
