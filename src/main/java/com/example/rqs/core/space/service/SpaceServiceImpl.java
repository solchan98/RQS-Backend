package com.example.rqs.core.space.service;

import com.example.rqs.core.common.exception.*;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.*;
import com.example.rqs.core.space.repository.*;
import com.example.rqs.core.space.service.dtos.*;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.repository.SpaceMemberRepository;
import com.example.rqs.core.spacemember.service.SpaceMemberAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpaceServiceImpl implements SpaceService {

    private final SpaceMemberAuthService spaceMemberAuthService;

    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;

    @Override
    @Transactional
    public SpaceResponse createSpace(CreateSpace createSpace) {
        Space space = Space.newSpace(createSpace.getTitle(), createSpace.getContent(), createSpace.getUrl(), createSpace.isVisibility());
        spaceRepository.save(space);
        SpaceMember spaceMember = SpaceMember.newSpaceAdmin(createSpace.getCreator(), space);
        spaceMemberRepository.save(spaceMember);
        return SpaceResponse.createBySpaceMember(space, spaceMember);
    }

    @Override
    @Transactional(readOnly = true)
    // TODO: 메서드 명 변경 필요
    public boolean isSpaceCreator(Member member, Long spaceId) {
        Optional<SpaceMember> spaceMemberOptional = spaceMemberRepository
                .getSpaceMember(member.getMemberId(), spaceId);
        if (spaceMemberOptional.isEmpty()) return false;
        return spaceMemberAuthService.isSpaceCreator(spaceMemberOptional.get());
    }

    @Override
    @Transactional
    public SpaceResponse updateTitle(UpdateSpace updateSpace) {
        SpaceMember spaceMember = spaceMemberRepository
                .getSpaceMember(updateSpace.getMember().getMemberId(), updateSpace.getSpaceId())
                .orElseThrow(BadRequestException::new);
        boolean updatable = spaceMemberAuthService.isUpdatableSpace(spaceMember);
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
        boolean updatable = spaceMemberAuthService.isUpdatableSpaceMemberRole(spaceAdmin);
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
        boolean isDeletable = spaceMemberAuthService.isDeletableSpace(spaceMember);
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
        boolean isDeletable = spaceMemberAuthService.isDeletableSpace(spaceMember);
        if (!isDeletable) throw new ForbiddenException();
        spaceRepository.deleteById(deleteSpace.getSpaceId());
    }
}
