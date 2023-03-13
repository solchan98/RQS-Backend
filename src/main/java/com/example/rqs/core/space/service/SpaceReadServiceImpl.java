package com.example.rqs.core.space.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.repository.SpaceRepository;
import com.example.rqs.core.space.service.dtos.*;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;
import com.example.rqs.core.spacemember.service.SpaceMemberAuthService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpaceReadServiceImpl implements SpaceReadService {

    private final SpaceRepository spaceRepository;
    private final SpaceMemberReadService smReadService;
    private final SpaceMemberAuthService smAuthService;

    @Override
    public Optional<Space> getSpace(Long spaceId) {
        return spaceRepository.findById(spaceId);
    }

    @Override
    public SpaceResponse getSpace(ReadSpace readSpace) {
        Space space = spaceRepository.findById(readSpace.getSpaceId()).orElseThrow(BadRequestException::new);

        if (Objects.isNull(readSpace.getMember())) {
            if (!space.isVisibility()) {
                throw new ForbiddenException();
            }
            return SpaceResponse.createByGuest(space);
        }

        Optional<SpaceMember> spaceMemberOptional = smReadService
                .getSpaceMember(readSpace.getMember().getMemberId(), readSpace.getSpaceId());
        if (spaceMemberOptional.isEmpty()) {
            if (!space.isVisibility()) {
                throw new ForbiddenException();
            }
            return SpaceResponse.createByGuest(space);
        }

        return SpaceResponse.createBySpaceMember(space, spaceMemberOptional.get());
    }

    @Override
    public List<SpaceResponse> getSpaces(ReadSpaces readSpaces) {
        return readSpaces.getType().equals("TRENDING")
                ? this.spaceRepository.getSpacesByTrending(readSpaces.getOffset())
                : this.spaceRepository.getSpaces(readSpaces.getLastAt());
    }

    @Override
    public List<SpaceResponse> getSpaces(ReadMembersSpaceList readMembersSpaces) {
        return spaceRepository.getMembersSpaces(
                readMembersSpaces.getMemberId(),
                readMembersSpaces.getTargetMemberId(),
                readMembersSpaces.getLastJoinedAt());
    }

    @Override
    public Map<SpaceRole, String> getJoinCodes(Member member, Long spaceId) {
        SpaceMember spaceMember = smReadService
                .getSpaceMember(member.getMemberId(), spaceId)
                .orElseThrow(ForbiddenException::new);

        boolean readable = smAuthService.isUpdatableSpaceMemberRole(spaceMember);
        if (!readable) {
            throw new ForbiddenException();
        }

        return spaceMember.getSpace().joinCode();
    }

    @Override
    public void checkReadableQuiz(Long memberId, Long spaceId) {
        Space space = spaceRepository
                .findById(spaceId)
                .orElseThrow(BadRequestException::new);

        if (!space.isVisibility()) {
            smReadService
                    .getSpaceMember(memberId, spaceId)
                    .orElseThrow(ForbiddenException::new);
        }
    }
}
