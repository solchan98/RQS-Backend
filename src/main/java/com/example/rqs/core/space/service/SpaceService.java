package com.example.rqs.core.space.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.service.dtos.*;

import java.util.List;

public interface SpaceService {

    SpaceResponse getSpace(ReadSpace readSpace);
    List<SpaceResponse> getSpaceList(ReadSpaceList readSpaceList);
    List<SpaceResponse> getMySpaceList(ReadSpaceList readSpaceList);

    List<SpaceMemberResponse> getSpaceMemberList(Long memberId, Long spaceId);

    SpaceResponse createSpace(CreateSpace createSpace);

    void checkIsCreatableInviteLink(Long spaceId, Long memberId);

    boolean isSpaceCreator(Member member, Long spaceId);

    SpaceResponse updateTitle(UpdateSpace updateSpace);

    void changeVisibility();


    SpaceMemberResponse addNewMember(Long spaceId, Member member);

    SpaceMemberResponse changeMemberRole(UpdateSpaceMemberRole updateSpaceMemberRole);

    void deleteMember(DeleteSpaceMember deleteSpaceMember);

    void deleteSpace(DeleteSpace deleteSpace);
}
