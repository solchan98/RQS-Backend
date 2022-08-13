package com.example.rqs.core.space.repository;

import com.example.rqs.core.space.SpaceMember;

import java.util.List;
import java.util.Optional;

public interface CustomSpaceMemberRepository {

    Optional<SpaceMember> getSpaceMember(Long memberId, Long spaceId);

    List<SpaceMember> getAllSpaceMember(Long memberId, Boolean isVisibility);
}
