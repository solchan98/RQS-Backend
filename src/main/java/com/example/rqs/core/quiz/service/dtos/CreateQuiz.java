package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateQuiz {

    private final Long spaceId;

    private final Member member;

    private final String question;

    private final List<String> answers;

    private final String type;

    private final String hint;

    private CreateQuiz(Long spaceId, Member member, String question, List<String> answers, String type, String hint) {
        this.spaceId = spaceId;
        this.member = member;
        this.question = question;
        this.answers = answers;
        this.type = type;
        this.hint = hint;
    }

    public static CreateQuiz of(Long spaceId, Member member, String question, List<String> answers, String type, String hint) {
        return new CreateQuiz(spaceId, member, question, answers, type, hint);
    }
}
