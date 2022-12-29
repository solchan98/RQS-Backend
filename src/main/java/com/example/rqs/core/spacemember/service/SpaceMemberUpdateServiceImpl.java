package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.spacemember.service.dtos.DeleteSpaceMember;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.spacemember.service.dtos.UpdateSpaceMemberRole;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.repository.SpaceMemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceMemberUpdateServiceImpl implements SpaceMemberUpdateService {

    private final SpaceMemberAuthService smAuthService;

    private final SpaceMemberRepository smRepository;

    @Override
    public SpaceMemberResponse changeSpaceMemberRole(UpdateSpaceMemberRole updateSpaceMemberRole) {
        SpaceMember spaceMember = smRepository.getSpaceMember(
                        updateSpaceMemberRole.getAdmin().getMemberId(),
                        updateSpaceMemberRole.getSpaceId())
                .orElseThrow(ForbiddenException::new);

        boolean isUpdatable = smAuthService.isUpdatableSpaceMemberRole(spaceMember);
        if (!isUpdatable) throw new ForbiddenException();

        SpaceMember targetSpaceMember = smRepository
                .findById(updateSpaceMemberRole.getChangedSpaceMemberId())
                .orElseThrow(BadRequestException::new);
        if (!targetSpaceMember.getSpace().getSpaceId().equals(spaceMember.getSpace().getSpaceId()))
            throw new BadRequestException();

        targetSpaceMember.updateRole(updateSpaceMemberRole.getNewRole());
        return SpaceMemberResponse.of(targetSpaceMember);
    }

    @Override
    public void deleteSpaceMember(DeleteSpaceMember deleteSpaceMember) {
        SpaceMember spaceMember = smRepository.getSpaceMember(
                        deleteSpaceMember.getMember().getMemberId(),
                        deleteSpaceMember.getSpaceId())
                .orElseThrow(ForbiddenException::new);

        boolean isDeletable = smAuthService.isDeletableSpace(spaceMember);
        if (!isDeletable) throw new ForbiddenException();

        SpaceMember willDeleteSpaceMember = smRepository
                .findById(deleteSpaceMember.getSpaceId())
                .orElseThrow(BadRequestException::new);
        if (!willDeleteSpaceMember.getSpace().getSpaceId().equals(spaceMember.getSpace().getSpaceId()))
            throw new BadRequestException();

        smRepository.delete(willDeleteSpaceMember);
    }
}
