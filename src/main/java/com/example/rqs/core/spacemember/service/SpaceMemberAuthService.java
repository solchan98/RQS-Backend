package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.spacemember.SpaceMember;

public interface SpaceMemberAuthService {
    boolean isSpaceCreator(SpaceMember spaceMember);
    boolean isCreatableItem(SpaceMember spaceMember);
    boolean isReadableSpaceMemberList(SpaceMember spaceMember);
    boolean isUpdatableSpace(SpaceMember spaceMember);
    boolean isUpdatableSpaceMemberRole(SpaceMember spaceMember);
    boolean isDeletableSpace(SpaceMember spaceMember);


}
