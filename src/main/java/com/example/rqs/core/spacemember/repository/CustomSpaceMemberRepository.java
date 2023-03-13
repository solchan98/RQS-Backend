package com.example.rqs.core.spacemember.repository;

import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;

import java.util.List;
import java.util.Optional;

public interface CustomSpaceMemberRepository {

    Optional<SpaceMember> getSpaceMember(Long memberId, Long spaceId);

    List<SpaceMemberResponse> getSpaceMemberResponses(Long spaceId);

    boolean existSpaceMember(Long memberId, Long spaceId);

    boolean existSpaceMember(Long spaceMemberId);
}
