package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class DeleteItem {

    private final Member member;

    private final Long itemId;

    private DeleteItem(Member member, Long itemId) {
        this.member = member;
        this.itemId = itemId;
    }

    public static DeleteItem of (Member member, Long itemId) {
        return new DeleteItem(member, itemId);
    }
}
