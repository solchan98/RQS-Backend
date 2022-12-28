package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;

public interface SpaceMemberRegisterService {
    SpaceMember createSpaceMember(Member member, Space space, SpaceRole spaceRole);
    SpaceMemberResponse addNewMember(Member member, Long spaceId);
}
