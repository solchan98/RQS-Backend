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
    @Transactional(readOnly = true)
    public SpaceMemberResponse changeMemberRole(UpdateSpaceMemberRole updateSpaceMemberRole) {
        SpaceMember spaceAdmin = spaceMemberRepository
                .getSpaceMember(updateSpaceMemberRole.getAdmin().getMemberId(), updateSpaceMemberRole.getSpaceId())
                .orElseThrow(BadRequestException::new);
        boolean updatable = spaceAdmin.isUpdatableMemberRole();
        if (!updatable)  throw new ForbiddenException();
        SpaceMember spaceMember = spaceMemberRepository
                .findById(updateSpaceMemberRole.getChangedSpaceMemberId())
                .orElseThrow(() -> new BadRequestException(RQSError.SPACE_MEMBER_NOT_FOUND));
        spaceMember.updateRole(updateSpaceMemberRole.getNewRole());
        return SpaceMemberResponse.of(spaceMember);
    }

    @Override
    @Transactional
    public void deleteMember(DeleteSpaceMember deleteSpaceMember) {
        SpaceMember spaceMember = spaceMemberRepository
                .getSpaceMember(deleteSpaceMember.getMember().getMemberId(), deleteSpaceMember.getSpaceId())
                .orElseThrow(BadRequestException::new);
        boolean isDeletable = spaceMember.isDeletableSpaceMember();
        if (!isDeletable) throw new ForbiddenException();
        boolean isSelfDelete = spaceMember.getSpaceMemberId().equals(deleteSpaceMember.getSpaceMemberId());
        if (isSelfDelete) throw new BadRequestException();
        SpaceMember willDeletedSpaceMember = spaceMemberRepository
                .findById(deleteSpaceMember.getSpaceMemberId())
                .orElseThrow(() -> new BadRequestException(RQSError.SPACE_MEMBER_NOT_FOUND));
        boolean isSpaceMember = willDeletedSpaceMember.getSpace().getSpaceId().equals(deleteSpaceMember.getSpaceId());
        if (!isSpaceMember) throw new BadRequestException(RQSError.SPACE_MEMBER_NOT_FOUND);
        spaceMemberRepository
                .delete(willDeletedSpaceMember);
    }

    @Override
    public void deleteSpace() {

    }
}
