package com.example.rqs.core.space.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.service.dtos.InviteSpaceSubject;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;

public interface SpaceInviteService {
    InviteSpaceSubject createInviteSpaceSubject(Member member, Long spaceId);

    SpaceMemberResponse join(Member member, Long spaceId, String joinCode);
}
