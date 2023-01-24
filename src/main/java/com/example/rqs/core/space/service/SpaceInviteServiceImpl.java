package com.example.rqs.core.space.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.service.dtos.InviteSpaceSubject;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;
import com.example.rqs.core.spacemember.service.SpaceMemberAuthService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;
import com.example.rqs.core.spacemember.service.SpaceMemberRegisterService;

import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpaceInviteServiceImpl implements SpaceInviteService {

    private final SpaceReadService spaceReadService;

    private final SpaceMemberReadService smReadService;
    private final SpaceMemberAuthService smAuthService;
    private final SpaceMemberRegisterService smRegisterService;

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

    @Override
    @Transactional
    public SpaceMemberResponse join(Member member, Long spaceId, String joinCode) {
        Space space = spaceReadService
                .getSpace(spaceId)
                .orElseThrow(BadRequestException::new);

        SpaceRole spaceRole = space.getRoleByJoinCode(joinCode);
        if (spaceRole == SpaceRole.GUEST) {
            throw new ForbiddenException();
        }
        SpaceMember spaceMember = smRegisterService.createSpaceMember(member, space, spaceRole);

        return SpaceMemberResponse.of(spaceMember);
    }
}
