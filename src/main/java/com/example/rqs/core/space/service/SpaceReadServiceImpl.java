package com.example.rqs.core.space.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.repository.SpaceRepository;
import com.example.rqs.core.space.service.dtos.*;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpaceReadServiceImpl implements SpaceReadService {

    private final SpaceRepository spaceRepository;
    private final SpaceMemberReadService smReadService;

    @Override
    public SpaceResponse getSpace(ReadSpace readSpace) {
        Space space = spaceRepository.findById(readSpace.getSpaceId()).orElseThrow(BadRequestException::new);
        Optional<SpaceMember> optionalSpaceMember = smReadService
                .getSpaceMember(readSpace.getMember().getMemberId(), readSpace.getSpaceId());

        boolean isGuest = Objects.isNull(readSpace.getMember()) || optionalSpaceMember.isEmpty();
        if (!isGuest) {
            return SpaceResponse.createBySpaceMember(space, optionalSpaceMember.get());
        }

        if (!space.isVisibility()) throw new ForbiddenException();
        return SpaceResponse.createByGuest(space);
    }

    @Override
    public List<SpaceResponse> getSpaceList(ReadSpaceList readSpaceList) {
        return readSpaceList.getType().equals("TRENDING")
                ? this.spaceRepository.getSpaceListByTrending(readSpaceList.getOffset())
                : this.spaceRepository.getSpaceList(readSpaceList.getLastAt());
    }

    @Override
    public List<SpaceResponse> getSpaceList(ReadMembersSpaceList readMembersSpaceList) {
        return spaceRepository.getMembersSpaceList(
                readMembersSpaceList.getMemberId(),
                readMembersSpaceList.getTargetMemberId(),
                readMembersSpaceList.getLastJoinedAt());
    }
}
