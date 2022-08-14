package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class ReadItem {

    private final Member member;

    private final Long lastId;

    private final Long spaceId;

    private ReadItem(Member member, Long lastId, Long spaceId) {
        this.member = member;
        this.lastId = lastId;
        this.spaceId = spaceId;
    }

    public static ReadItem of(Member member, Long lastId, Long spaceId) {
        return new ReadItem(member, lastId, spaceId);
    }
}
