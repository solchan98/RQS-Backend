package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class UpdateSpaceMemberRole {

    private final Member admin;

    private final Long spaceId;

    private final Long changedSpaceMemberId;

    private final String newRole;

    private UpdateSpaceMemberRole(Member admin, Long spaceId, Long changedSpaceMemberId, String newRole) {
        this.admin = admin;
        this.spaceId = spaceId;
        this.changedSpaceMemberId = changedSpaceMemberId;
        this.newRole = newRole;
    }

    public static UpdateSpaceMemberRole of(Member admin, Long spaceId, Long changedSpaceMemberId, String newRole) {
        return new UpdateSpaceMemberRole(admin, spaceId, changedSpaceMemberId, newRole);
    }
}
