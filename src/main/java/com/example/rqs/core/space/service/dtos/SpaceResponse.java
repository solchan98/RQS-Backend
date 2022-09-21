package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.space.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpaceResponse {

    private Long spaceId;

    private String title;

    private boolean visibility;

    private Integer spaceMemberCount;

    private Integer itemCount;

    private LocalDateTime memberJoinedAt;

    private SpaceRole authority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static SpaceResponse createByGuest(Space space) {
        return new SpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.isVisibility(),
                space.getSpaceMemberList().size(),
                space.getItemList().size(),
                null,
                SpaceRole.GUEST,
                space.getCreatedAt(),
                space.getUpdatedAt());
    }

    public static SpaceResponse createBySpaceMember(Space space, SpaceMember reader) {
        return new SpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.isVisibility(),
                space.getSpaceMemberList().size(),
                space.getItemList().size(),
                reader.getJoinedAt(),
                reader.getRole(),
                space.getCreatedAt(),
                space.getUpdatedAt());
    }
}
