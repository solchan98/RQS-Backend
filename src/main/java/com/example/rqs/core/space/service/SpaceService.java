package com.example.rqs.core.space.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.service.dtos.*;

public interface SpaceService {

    SpaceResponse createSpace(CreateSpace createSpace);

    boolean isSpaceCreator(Member member, Long spaceId);

    SpaceResponse updateTitle(UpdateSpace updateSpace);

    void changeVisibility();


    SpaceMemberResponse addNewMember(Long spaceId, Member member);

    SpaceMemberResponse changeMemberRole(UpdateSpaceMemberRole updateSpaceMemberRole);

    void deleteMember(DeleteSpaceMember deleteSpaceMember);

    void deleteSpace(DeleteSpace deleteSpace);
}
