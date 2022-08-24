package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class DeleteSpace {

    private final Member member;

    private final Long spaceId;

    private DeleteSpace(Member member, Long spaceId) {
        this.member = member;
        this.spaceId = spaceId;
    }

    public static DeleteSpace of(Member member, Long spaceId) {
        return new DeleteSpace(member, spaceId);
    }
}
