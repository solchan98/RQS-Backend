package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class ReadQuiz {

    private final Member member;
    private final Long itemId;

    private ReadQuiz(Member member, Long itemId) {
        this.member = member;
        this.itemId = itemId;
    }

    public static ReadQuiz of(Member member, Long itemId) {
        return new ReadQuiz(member, itemId);
    }
    public static ReadQuiz of(Long itemId) {
        return new ReadQuiz(null, itemId);
    }
}
