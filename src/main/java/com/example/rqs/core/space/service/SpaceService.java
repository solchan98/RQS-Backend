package com.example.rqs.core.space.service;

import com.example.rqs.core.space.service.dtos.*;

public interface SpaceService {
    SpaceResponse createSpace(CreateSpace createSpace);

    SpaceResponse updateTitle(UpdateSpace updateSpace);

    void changeVisibility();

    void addNewMember();

    void changeMemberRole();

    void deleteMember();

    void deleteSpace();
}
