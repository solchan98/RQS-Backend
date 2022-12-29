package com.example.rqs.core.space.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.service.dtos.SpaceResponse;

public interface SpaceUpdateService {

    SpaceResponse updateTitle(Member member, Long spaceId, String title);
    SpaceResponse changeVisibility(Member member, Long spaceId, boolean visibility);
    void deleteSpace(Member member, Long spaceId);
}
