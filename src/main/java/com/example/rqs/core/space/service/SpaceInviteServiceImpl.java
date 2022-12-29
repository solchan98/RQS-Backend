package com.example.rqs.core.space.service;

import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.service.dtos.InviteSpaceSubject;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.service.SpaceMemberAuthService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpaceInviteServiceImpl implements SpaceInviteService {

    private final SpaceMemberReadService smReadService;
    private final SpaceMemberAuthService smAuthService;

    @Override
    public InviteSpaceSubject createInviteSpaceSubject(Member member, Long spaceId) {
        SpaceMember spaceMember = smReadService
                .getSpaceMember(member.getMemberId(), spaceId)
                .orElseThrow(ForbiddenException::new);

        boolean isUpdatable = smAuthService.isUpdatableSpaceMemberRole(spaceMember);
        if (!isUpdatable) throw new ForbiddenException();

        Space space = spaceMember.getSpace();
        return InviteSpaceSubject.of(space.getSpaceId(), space.getTitle(), member.getMemberId(), member.getNickname());
    }
}
