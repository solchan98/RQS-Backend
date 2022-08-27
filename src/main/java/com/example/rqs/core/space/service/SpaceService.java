package com.example.rqs.core.space.service;

import com.example.rqs.core.space.service.dtos.*;

import java.util.List;

public interface SpaceService {

    SpaceResponse getSpace(Long memberId, Long spaceId);
    List<SpaceResponse> getMySpaceList(ReadSpace readSpace);

    List<SpaceMemberResponse> getSpaceMemberList(Long memberId, Long spaceId);

    SpaceResponse createSpace(CreateSpace createSpace);

    SpaceResponse updateTitle(UpdateSpace updateSpace);

    void changeVisibility();

    void addNewMember();

    SpaceMemberResponse changeMemberRole(UpdateSpaceMemberRole updateSpaceMemberRole);

    void deleteMember(DeleteSpaceMember deleteSpaceMember);

    void deleteSpace(DeleteSpace deleteSpace);
}
