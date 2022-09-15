package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class SpaceResponse {

    private Long spaceId;

    private String title;

    private boolean visibility;

    private Integer spaceMemberCount;

    private Integer itemCount;

    private String authority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private SpaceResponse(Long spaceId, String title, boolean visibility, Integer itemCount, Integer spaceMemberCount, String authority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.spaceId = spaceId;
        this.title = title;
        this.visibility = visibility;
        this.itemCount = itemCount;
        this.spaceMemberCount = spaceMemberCount;
        this.authority = authority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SpaceResponse createByGuest(Space space) {
        return new SpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.isVisibility(),
                space.getItemList().size(),
                space.getSpaceMemberList().size(),
                "GUEST",
                space.getCreatedAt(),
                space.getUpdatedAt());
    }

    public static SpaceResponse createBySpaceMember(Space space, SpaceMember reader) {
        return new SpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.isVisibility(),
                space.getItemList().size(),
                space.getSpaceMemberList().size(),
                reader.getRole(),
                space.getCreatedAt(),
                space.getUpdatedAt());
    }
}
