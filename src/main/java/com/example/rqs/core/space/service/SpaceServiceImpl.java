package com.example.rqs.core.space.service;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;
import com.example.rqs.core.space.repository.*;
import com.example.rqs.core.space.service.dtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpaceServiceImpl implements SpaceService{

    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;

    public SpaceServiceImpl (SpaceRepository spaceRepository, SpaceMemberRepository spaceMemberRepository) {
        this.spaceRepository = spaceRepository;
        this.spaceMemberRepository = spaceMemberRepository;
    }

    @Transactional
    @Override
    public SpaceResponse createSpace(CreateSpace createSpace) {
        Space space = Space.newSpace(createSpace.getTitle(), createSpace.isVisibility());
        spaceRepository.save(space);
        SpaceMember spaceMember = SpaceMember.newSpaceAdmin(createSpace.getCreator(), space);
        spaceMemberRepository.save(spaceMember);
        return SpaceResponse.of(space);
    }

    @Override
    public void updateTitle() {

    }

    @Override
    public void changeVisibility() {

    }

    @Override
    public void addNewMember() {

    }

    @Override
    public void changeMemberRole() {

    }

    @Override
    public void deleteMember() {

    }

    @Override
    public void deleteSpace() {

    }
}
