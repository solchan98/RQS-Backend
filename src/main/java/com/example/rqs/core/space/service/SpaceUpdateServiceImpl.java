package com.example.rqs.core.space.service;

import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.repository.SpaceRepository;
import com.example.rqs.core.space.service.dtos.SpaceResponse;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.service.SpaceMemberAuthService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceUpdateServiceImpl implements SpaceUpdateService {

    private final SpaceMemberReadService smReadService;
    private final SpaceMemberAuthService smAuthService;

    private final SpaceRepository spaceRepository;

    @Override
    public SpaceResponse updateTitle(Member member, Long spaceId, String title) {
        SpaceMember spaceMember = smReadService
                .getSpaceMember(member.getMemberId(), spaceId)
                .orElseThrow(ForbiddenException::new);

        boolean isUpdatable = smAuthService.isUpdatableSpace(spaceMember);
        if (!isUpdatable) throw new ForbiddenException();

        Space space = spaceMember.getSpace();
        space.updateTitle(title);
        return SpaceResponse.createBySpaceMember(space, spaceMember);
    }

    @Override
    public SpaceResponse changeVisibility(Member member, Long spaceId, boolean visibility) {
        SpaceMember spaceMember = smReadService
                .getSpaceMember(member.getMemberId(), spaceId)
                .orElseThrow(ForbiddenException::new);

        boolean isUpdatable = smAuthService.isUpdatableSpace(spaceMember);
        if (!isUpdatable) throw new ForbiddenException();

        Space space = spaceMember.getSpace();
        space.changeVisibility(visibility);
        return SpaceResponse.createBySpaceMember(space, spaceMember);
    }

    @Override
    public void deleteSpace(Member member, Long spaceId) {
        SpaceMember spaceMember = smReadService
                .getSpaceMember(member.getMemberId(), spaceId)
                .orElseThrow(ForbiddenException::new);

        boolean isDeletable = smAuthService.isDeletableSpace(spaceMember);
        if (!isDeletable) throw new ForbiddenException();

        spaceRepository.deleteById(spaceId);
    }
}
