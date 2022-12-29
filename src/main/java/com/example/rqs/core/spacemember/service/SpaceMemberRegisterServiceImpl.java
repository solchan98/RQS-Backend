package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.repository.SpaceRepository;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;
import com.example.rqs.core.spacemember.repository.SpaceMemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceMemberRegisterServiceImpl implements SpaceMemberRegisterService {

    private final SpaceMemberRepository spaceMemberRepository;
    private final SpaceRepository spaceRepository;

    @Override
    public SpaceMember createSpaceMember(Member member, Space space, SpaceRole spaceRole) {
        SpaceMember spaceMember = SpaceMember.of(member, space, spaceRole);
        return spaceMemberRepository.save(spaceMember);
    }

    @Override
    public SpaceMemberResponse addNewMember(Member member, Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new BadRequestException(RQSError.SPACE_IS_NOT_EXIST));

        boolean existSpaceMember = spaceMemberRepository.existSpaceMember(member.getMemberId(), spaceId);
        if (existSpaceMember) throw new BadRequestException(RQSError.SPACE_MEMBER_ALREADY_EXIST);

        SpaceMember spaceMember = this.createSpaceMember(member, space, SpaceRole.MEMBER);
        return SpaceMemberResponse.of(spaceMember);
    }
}
