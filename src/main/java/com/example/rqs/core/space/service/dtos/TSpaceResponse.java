package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;
import com.example.rqs.core.space.SpaceRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class TSpaceResponse {

    private Long spaceId;

    private String title;

    private boolean visibility;

    private Long spaceMemberCount;

    private Long itemCount;

    private LocalDateTime memberJoinedAt;

    private SpaceRole authority = SpaceRole.GUEST;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static TSpaceResponse createByGuest(Space space) {
        return new TSpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.isVisibility(),
                (long) space.getSpaceMemberList().size(),
                (long) space.getItemList().size(),
                null,
                SpaceRole.GUEST,
                space.getCreatedAt(),
                space.getUpdatedAt());
    }

    public static TSpaceResponse createBySpaceMember(Space space, SpaceMember reader) {
        return new TSpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.isVisibility(),
                (long) space.getSpaceMemberList().size(),
                (long) space.getItemList().size(),
                reader.getJoinedAt(),
                reader.getRole(),
                space.getCreatedAt(),
                space.getUpdatedAt());
    }
}
