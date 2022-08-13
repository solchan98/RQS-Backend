package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class CreateItem {

    private final Long spaceId;

    private final Member member;

    private final String question;

    private final String answer;

    private final String hint;

    private CreateItem(Long spaceId, Member member, String question, String answer, String hint) {
        this.spaceId = spaceId;
        this.member = member;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
    }

    public static CreateItem of(Long spaceId, Member member, String question, String answer, String hint) {
        return new CreateItem(spaceId, member, question, answer, hint);
    }
}
