package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.spacemember.SpaceMember;

import java.util.List;
import java.util.Optional;

public interface SpaceMemberReadService {

    Optional<SpaceMember> getSpaceMember(long memberId, long spaceId);
    List<SpaceMemberResponse> getSpaceMemberList(long memberId, long spaceId);
}
