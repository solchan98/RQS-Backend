package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SpaceMemberResponse {

    private Long spaceMemberId;

    private String email;

    private String nickname;

    private LocalDateTime joinedAt;

    private SpaceRole role;

    private SpaceMemberResponse(Long spaceMemberId, LocalDateTime joinedAt, SpaceRole role, String email, String nickname) {
        this.spaceMemberId = spaceMemberId;
        this.email = email;
        this.nickname = nickname;
        this.joinedAt = joinedAt;
        this.role = role;
    }

    public static SpaceMemberResponse of (SpaceMember spaceMember) {
        return new SpaceMemberResponse(
                spaceMember.getSpaceMemberId(),
                spaceMember.getJoinedAt(),
                spaceMember.getRole(),
                spaceMember.getMember().getEmail(),
                spaceMember.getMember().getNickname());
    }
}
