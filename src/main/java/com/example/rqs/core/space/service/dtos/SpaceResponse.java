package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SpaceResponse {

    private final Long spaceId;

    private final String title;

    private final boolean visibility;

    private final List<SpaceMemberResponse> spaceMemberList;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private SpaceResponse(Long spaceId, String title, boolean visibility, List<SpaceMember> spaceMemberList, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.spaceId = spaceId;
        this.title = title;
        this.visibility = visibility;
        this.spaceMemberList = spaceMemberList.stream().map(SpaceMemberResponse::of).collect(Collectors.toList());
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SpaceResponse of(Space space) {
        return new SpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.isVisibility(),
                space.getSpaceMemberList(),
                space.getCreatedAt(),
                space.getUpdatedAt());
    }
}
