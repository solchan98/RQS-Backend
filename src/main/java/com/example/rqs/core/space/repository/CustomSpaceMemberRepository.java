package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.SpaceMember;
import com.example.rqs.core.space.service.dtos.SpaceMemberResponse;

import java.util.List;
import java.util.Optional;

public interface CustomSpaceMemberRepository {

    Optional<SpaceMember> getSpaceMember(Long memberId, Long spaceId);

    List<SpaceMemberResponse> getSpaceMemberResponseList(Long spaceId);

    boolean existSpaceMember(Long memberId, Long spaceId);

    boolean existSpaceMember(Long spaceMemberId);
}
