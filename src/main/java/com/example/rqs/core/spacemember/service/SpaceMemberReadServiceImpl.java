package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.repository.SpaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpaceMemberReadServiceImpl implements SpaceMemberReadService {

    private final SpaceMemberRepository spaceMemberRepository;
    private final SpaceMemberAuthService spaceMemberAuthService;

    @Override
    public Optional<SpaceMember> getSpaceMember(long memberId, long spaceId) {
        return spaceMemberRepository.getSpaceMember(memberId, spaceId);
    }

    @Override
    public List<SpaceMemberResponse> getSpaceMemberList(long memberId, long spaceId) {
        SpaceMember spaceMember = spaceMemberRepository
                .getSpaceMember(memberId, spaceId)
                .orElseThrow(ForbiddenException::new);

        boolean readable = spaceMemberAuthService.isReadableSpaceMemberList(spaceMember);

        if (!readable) throw new ForbiddenException();
        return spaceMemberRepository.getSpaceMemberResponseList(spaceId);
    }
}
