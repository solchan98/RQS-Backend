package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class UpdateQuiz {

    private final Member member;

    private final Long quizId;

    private final String question;

    private final String answer;

    private final String hint;

    private UpdateQuiz(Member member, Long quizId, String question, String answer, String hint) {
        this.member = member;
        this.quizId = quizId;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
    }

    public static UpdateQuiz of(Member member, Long quizId, String question, String answer, String hint) {
        return new UpdateQuiz(member, quizId, question, answer, hint);
    }
}
