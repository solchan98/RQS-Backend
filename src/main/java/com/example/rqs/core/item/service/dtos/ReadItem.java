package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class ReadItem {

    private final Member member;

    private final Long itemId;

    private ReadItem(Member member, Long itemId) {
        this.member = member;
        this.itemId = itemId;
    }

    public static ReadItem of(Member member, Long itemId) {
        return new ReadItem(member, itemId);
    }
    public static ReadItem of(Long itemId) {
        return new ReadItem(null, itemId);
    }
}
