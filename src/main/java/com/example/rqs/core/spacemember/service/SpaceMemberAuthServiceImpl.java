package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.spacemember.*;
import org.springframework.stereotype.Service;

@Service
public class SpaceMemberAuthServiceImpl implements SpaceMemberAuthService {

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
    public boolean isUpdatableSpaceMemberRole(SpaceMember spaceMember) {
        return spaceMember.getRole().goe(SpaceRole.ADMIN);
    }

    @Override
    public boolean isDeletableSpace(SpaceMember spaceMember) {
        return spaceMember.getRole().goe(SpaceRole.ADMIN);
    }
}
