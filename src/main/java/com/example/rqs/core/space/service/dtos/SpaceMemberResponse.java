package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.space.SpaceMember;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SpaceMemberResponse {

    private final Long spaceMemberId;

    private final String email;

    private final String nickname;

    private final LocalDateTime joinedAt;

    private final String role;

    private SpaceMemberResponse(Long spaceMemberId, LocalDateTime joinedAt, String role, String email, String nickname) {
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
