package com.example.rqs.core.space.service;

import com.example.rqs.core.common.exception.*;
import com.example.rqs.core.space.*;
import com.example.rqs.core.space.repository.*;
import com.example.rqs.core.space.service.dtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpaceServiceImpl implements SpaceService{

    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;

    public SpaceServiceImpl (SpaceRepository spaceRepository, SpaceMemberRepository spaceMemberRepository) {
        this.spaceRepository = spaceRepository;
        this.spaceMemberRepository = spaceMemberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpaceResponse> getMySpaceList(ReadSpace readSpace) {
        return spaceMemberRepository.getSpaceResponseList(
                readSpace.getMember().getMemberId(),
                readSpace.getLastJoinedAt(),
                readSpace.getVisibility());
    }

    @Override
    @Transactional
    public SpaceResponse createSpace(CreateSpace createSpace) {
        Space space = Space.newSpace(createSpace.getTitle(), createSpace.isVisibility());
        spaceRepository.save(space);
        SpaceMember spaceMember = SpaceMember.newSpaceAdmin(createSpace.getCreator(), space);
        spaceMemberRepository.save(spaceMember);
        return SpaceResponse.of(space);
    }

    @Override
    @Transactional
    public SpaceResponse updateTitle(UpdateSpace updateSpace) {
        SpaceMember spaceMember = spaceMemberRepository
                .getSpaceMember(updateSpace.getMember().getMemberId(), updateSpace.getSpaceId())
                .orElseThrow(BadRequestException::new);
        boolean updatable = spaceMember.isUpdatable();
        if (!updatable) throw new ForbiddenException();
        Space space = spaceMember.updateSpaceTitle(updateSpace.getTitle());
        return SpaceResponse.of(space);
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
