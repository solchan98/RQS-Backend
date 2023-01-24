package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.spacemember.SpaceRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinSpace {

    private Long spaceId;

    private String title;

    private String content;

    private Long spaceMemberCount;

    private Long itemCount;

    private boolean visibility;

    private SpaceRole spaceRole;

    private JoinSpace(Long spaceId, String title, String content, Long spaceMemberCount, Long itemCount, boolean visibility, SpaceRole spaceRole) {
        this.spaceId = spaceId;
        this.title = title;
        this.content = content;
        this.spaceMemberCount = spaceMemberCount;
        this.itemCount = itemCount;
        this.visibility = visibility;
        this.spaceRole = spaceRole;
    }

    public static JoinSpace of(SpaceResponse spaceResponse, SpaceRole spaceRole) {
        return new JoinSpace(
                spaceResponse.getSpaceId(),
                spaceResponse.getTitle(),
                spaceResponse.getContent(),
                spaceResponse.getSpaceMemberCount(),
                spaceResponse.getItemCount(),
                spaceResponse.isVisibility(),
                spaceRole
        );
    }
}
