package com.example.rqs.core.spacemember.service.dtos;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.spacemember.SpaceRole;
import lombok.Getter;

@Getter
public class UpdateSpaceMemberRole {

    private final Member admin;

    private final Long spaceId;

    private final Long changedSpaceMemberId;

    private final SpaceRole newRole;

    private UpdateSpaceMemberRole(Member admin, Long spaceId, Long changedSpaceMemberId, SpaceRole newRole) {
        this.admin = admin;
        this.spaceId = spaceId;
        this.changedSpaceMemberId = changedSpaceMemberId;
        this.newRole = newRole;
    }

    public static UpdateSpaceMemberRole of(Member admin, Long spaceId, Long changedSpaceMemberId, SpaceRole newRole) {
        return new UpdateSpaceMemberRole(admin, spaceId, changedSpaceMemberId, newRole);
    }
}
