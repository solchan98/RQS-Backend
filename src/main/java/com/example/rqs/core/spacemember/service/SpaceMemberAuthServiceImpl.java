package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.spacemember.*;
import com.example.rqs.core.spacemember.repository.SpaceMemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpaceMemberAuthServiceImpl implements SpaceMemberAuthService {

    private final SpaceMemberRepository spaceMemberRepository;

    @Override
    public boolean isSpaceCreator(SpaceMember spaceMember) {
        // TODO: Creator를 ADMIN이 아닌 CREATOR 추가 권한 생성하기
        return spaceMember.getRole().goe(SpaceRole.ADMIN);
    }

    @Override
    public boolean isCreatableItem(SpaceMember spaceMember) {
        return spaceMember.getRole().goe(SpaceRole.MEMBER);
    }

    @Override
    public boolean isReadableSpaceMemberList(SpaceMember spaceMember) {
        return spaceMember.getRole().goe(SpaceRole.ADMIN);
    }

    @Override
    public boolean isUpdatableSpace(SpaceMember spaceMember) {
        return spaceMember.getRole().goe(SpaceRole.ADMIN);
    }

    @Override
    public boolean isUpdatableSpace(Long memberId, Long spaceId) {
        Optional<SpaceMember> spaceMemberOptional = spaceMemberRepository.getSpaceMember(memberId, spaceId);
        if (spaceMemberOptional.isEmpty()) return false;
        return this.isUpdatableSpace(spaceMemberOptional.get());
    }

    @Override
    public boolean isUpdatableSpaceMemberRole(SpaceMember spaceMember) {
        return spaceMember.getRole().goe(SpaceRole.ADMIN);
    }

    @Override
    public boolean isDeletableSpace(SpaceMember spaceMember) {
        return spaceMember.getRole().goe(SpaceRole.ADMIN);
    }
}
