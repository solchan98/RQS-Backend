package com.example.rqs.core.space.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.service.dtos.InviteSpaceSubject;

public interface SpaceInviteService {
    InviteSpaceSubject createInviteSpaceSubject(Member member, Long spaceId);
}
