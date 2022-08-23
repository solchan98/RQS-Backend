package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class UpdateItem {

    private final Member member;

    private final Long itemId;

    private final String question;

    private final String answer;

    private final String hint;

    private UpdateItem(Member member, Long itemId, String question, String answer, String hint) {
        this.member = member;
        this.itemId = itemId;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
    }

    public static UpdateItem of(Member member, Long itemId, String question, String answer, String hint) {
        return new UpdateItem(member, itemId, question, answer, hint);
    }
}
