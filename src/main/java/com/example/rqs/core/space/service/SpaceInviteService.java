package com.example.rqs.core.space.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.service.dtos.JoinSpace;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;

public interface SpaceInviteService {
    JoinSpace checkJoinSpace(Long spaceId, String joinCode);
    SpaceMemberResponse join(Member member, Long spaceId, String joinCode);
}
