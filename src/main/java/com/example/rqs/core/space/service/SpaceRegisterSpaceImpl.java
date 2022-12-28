package com.example.rqs.core.space.service;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.repository.SpaceRepository;
import com.example.rqs.core.space.service.dtos.CreateSpace;
import com.example.rqs.core.space.service.dtos.SpaceResponse;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;
import com.example.rqs.core.spacemember.service.SpaceMemberRegisterService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceRegisterSpaceImpl implements SpaceRegisterService {

    private final SpaceRepository spaceRepository;
    private final SpaceMemberRegisterService smRegisterService;

    @Override
    public SpaceResponse createSpace(CreateSpace createSpace) {
        Space space = Space.newSpace(createSpace.getTitle(), createSpace.getContent(), createSpace.getUrl(), createSpace.isVisibility());
        spaceRepository.save(space);

        SpaceMember spaceMember = smRegisterService.createSpaceMember(createSpace.getCreator(), space, SpaceRole.ADMIN);
        return SpaceResponse.createBySpaceMember(space, spaceMember);
    }
}
