package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class ReadSpace {

    private final Member member;

    private final Long spaceId;

    private ReadSpace(Member member, Long spaceId) {
        this.member = member;
        this.spaceId = spaceId;
    }

    public static ReadSpace of(Member member, Long spaceId) {
        return new ReadSpace(member, spaceId);
    }

    public static ReadSpace of(Long spaceId) {
        return new ReadSpace(null, spaceId);
    }
}
