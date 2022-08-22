package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class DeleteSpaceMember {

    private final Member member;

    private final Long spaceId;

    private final Long spaceMemberId;

    private DeleteSpaceMember(Member member, Long spaceId, Long spaceMemberId) {
        this.member = member;
        this.spaceId = spaceId;
        this.spaceMemberId = spaceMemberId;
    }

    public static DeleteSpaceMember of(Member member, Long spaceId, Long spaceMemberId) {
        return new DeleteSpaceMember(member, spaceId, spaceMemberId);
    }
}
