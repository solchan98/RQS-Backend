package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SpaceResponse {

    private Long spaceId;

    private String title;

    private boolean visibility;

    private List<SpaceMemberResponse> spaceMemberList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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

    public void setSpaceMemberList(List<SpaceMemberResponse> spaceMemberList) {
        this.spaceMemberList = spaceMemberList;
    }
}
