package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class CreateQuiz {

    private final Long spaceId;

    private final Member member;

    private final String question;

    private final String answer;

    private final String hint;

    private CreateQuiz(Long spaceId, Member member, String question, String answer, String hint) {
        this.spaceId = spaceId;
        this.member = member;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
    }

    public static CreateQuiz of(Long spaceId, Member member, String question, String answer, String hint) {
        return new CreateQuiz(spaceId, member, question, answer, hint);
    }
}
