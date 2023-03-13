package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class ReadQuizzes {

    private final Member member;

    private final Long lastId;

    private final Long spaceId;

    private ReadQuizzes(Member member, Long lastId, Long spaceId) {
        this.member = member;
        this.lastId = lastId;
        this.spaceId = spaceId;
    }

    public static ReadQuizzes of(Member member, Long lastId, Long spaceId) {
        return new ReadQuizzes(member, lastId, spaceId);
    }

    public static ReadQuizzes of(Long lastId, Long spaceId) {
        return new ReadQuizzes(null, lastId, spaceId);
    }
}
