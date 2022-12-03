package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class ReadItemList {

    private final Member member;

    private final Long lastId;

    private final Long spaceId;

    private ReadItemList(Member member, Long lastId, Long spaceId) {
        this.member = member;
        this.lastId = lastId;
        this.spaceId = spaceId;
    }

    public static ReadItemList of(Member member, Long lastId, Long spaceId) {
        return new ReadItemList(member, lastId, spaceId);
    }

    public static ReadItemList of(Long lastId, Long spaceId) {
        return new ReadItemList(null, lastId, spaceId);
    }
}
