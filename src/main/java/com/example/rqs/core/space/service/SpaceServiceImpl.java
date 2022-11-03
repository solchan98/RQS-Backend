package com.example.rqs.core.space.service;

import com.example.rqs.core.common.exception.*;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.*;
import com.example.rqs.core.space.repository.*;
import com.example.rqs.core.space.service.dtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public SpaceResponse getSpace(ReadSpace readSpace) {
        Space space = spaceRepository
                .findById(readSpace.getSpaceId())
                .orElseThrow(BadRequestException::new);
        Optional<SpaceMember> spaceMember = spaceMemberRepository
                .getSpaceMember(readSpace.getMember().getMemberId(), readSpace.getSpaceId());
        if (!space.isVisibility() && spaceMember.isEmpty()) throw new ForbiddenException();
        return spaceMember
                .map(member -> SpaceResponse.createBySpaceMember(space, member))
                .orElseGet(() -> SpaceResponse.createByGuest(space));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpaceResponse> getMySpaceList(ReadSpaceList readSpaceList) {
        return spaceMemberRepository.getSpaceResponseList(
                readSpaceList.getMember().getMemberId(),
                readSpaceList.getLastJoinedAt(),
                readSpaceList.getVisibility());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpaceMemberResponse> getSpaceMemberList(Long memberId, Long spaceId) {
        SpaceMember spaceMember = spaceMemberRepository
                .getSpaceMember(memberId, spaceId)
                .orElseThrow(BadRequestException::new);
        boolean readable = spaceMember.isReadableSpaceMemberList();
        if (!readable) throw new ForbiddenException();
        return spaceMemberRepository
                .getSpaceMemberResponseList(spaceId);
    }

    @Override
    @Transactional
    public SpaceResponse createSpace(CreateSpace createSpace) {
        Space space = Space.newSpace(createSpace.getTitle(), createSpace.isVisibility());
        spaceRepository.save(space);
        SpaceMember spaceMember = SpaceMember.newSpaceAdmin(createSpace.getCreator(), space);
        spaceMemberRepository.save(spaceMember);
        return SpaceResponse.createBySpaceMember(space, spaceMember);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkIsCreatableInviteLink(Long memberId, Long spaceId) {
        SpaceMember spaceMember = spaceMemberRepository
                .getSpaceMember(memberId, spaceId)
                .orElseThrow(ForbiddenException::new);
        boolean updatableMemberRole = spaceMember.isUpdatableMemberRole();
        if (!updatableMemberRole) throw new ForbiddenException();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSpaceCreator(Member member, Long spaceId) {
        Optional<SpaceMember> spaceMemberOptional = spaceMemberRepository
                .getSpaceMember(member.getMemberId(), spaceId);
        if (spaceMemberOptional.isEmpty()) return false;
        SpaceMember spaceMember = spaceMemberOptional.get();
        return spaceMember.isCreator();
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
        return SpaceResponse.createBySpaceMember(space, spaceMember);
    }

    @Override
    public void changeVisibility() {

    }

    @Override
    @Transactional
    public SpaceMemberResponse addNewMember(Long spaceId, Member member) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new BadRequestException(RQSError.SPACE_IS_NOT_EXIST));
        boolean existSpaceMember = spaceMemberRepository
                .existSpaceMember(member.getMemberId(), spaceId);
        if (existSpaceMember) throw new BadRequestException(RQSError.SPACE_MEMBER_ALREADY_EXIST);
        SpaceMember spaceMember = SpaceMember.newSpaceMember(member, space);
        spaceMemberRepository.save(spaceMember);
        return SpaceMemberResponse.of(spaceMember);
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteSpace(DeleteSpace deleteSpace) {
        SpaceMember spaceMember = spaceMemberRepository
                .getSpaceMember(deleteSpace.getMember().getMemberId(), deleteSpace.getSpaceId())
                .orElseThrow(ForbiddenException::new);
        boolean isDeletable = spaceMember.isDeletableSpace();
        if (!isDeletable) throw new ForbiddenException();
        spaceRepository
                .deleteById(deleteSpace.getSpaceId());
    }
}
