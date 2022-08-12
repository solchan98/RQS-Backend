package com.example.rqs.core.space.service;

import com.example.rqs.core.space.service.dtos.*;

public interface SpaceService {
    SpaceResponse createSpace(CreateSpace createSpace);

    void updateTitle();

    void changeVisibility();

    void addNewMember();

    void changeMemberRole();

    void deleteMember();

    void deleteSpace();
}
