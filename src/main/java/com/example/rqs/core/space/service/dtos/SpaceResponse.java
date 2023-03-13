package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class SpaceResponse {

    private Long spaceId;

    private String title;

    private String content;

    private String imageUrl;

    private boolean visibility;

    private Long spaceMemberCount;

    private Long quizCount;

    private LocalDateTime memberJoinedAt;

    private SpaceRole authority = SpaceRole.GUEST;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static SpaceResponse createByGuest(Space space) {
        return new SpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.getContent(),
                space.getUrl(),
                space.isVisibility(),
                (long) space.getSpaceMembers().size(),
                (long) space.getQuizzes().size(),
                null,
                SpaceRole.GUEST,
                space.getCreatedAt(),
                space.getUpdatedAt());
    }

    public static SpaceResponse createBySpaceMember(Space space, SpaceMember reader) {
        return new SpaceResponse(
                space.getSpaceId(),
                space.getTitle(),
                space.getContent(),
                space.getUrl(),
                space.isVisibility(),
                (long) space.getSpaceMembers().size(),
                (long) space.getQuizzes().size(),
                reader.getJoinedAt(),
                reader.getRole(),
                space.getCreatedAt(),
                space.getUpdatedAt());
    }

    public void setSpaceMemberCount(Long spaceMemberCount) {
        this.spaceMemberCount = spaceMemberCount;
    }

    public void setMemberData(SpaceRole spaceRole, LocalDateTime memberJoinedAt) {
        this.authority = spaceRole;
        this.memberJoinedAt = memberJoinedAt;
    }
}
