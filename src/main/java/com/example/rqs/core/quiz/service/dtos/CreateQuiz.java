package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateQuiz {

    private final Long spaceId;

    private final Member member;

    private final String question;

    private final List<CreateAnswer> createAnswers;

    private final String type;

    private final String hint;

    private CreateQuiz(Long spaceId, Member member, String question, List<CreateAnswer> createAnswers, String type, String hint) {
        this.spaceId = spaceId;
        this.member = member;
        this.question = question;
        this.createAnswers = createAnswers;
        this.type = type;
        this.hint = hint;
    }

    public static CreateQuiz of(Long spaceId, Member member, String question, List<CreateAnswer> createAnswers, String type, String hint) {
        return new CreateQuiz(spaceId, member, question, createAnswers, type, hint);
    }
}
