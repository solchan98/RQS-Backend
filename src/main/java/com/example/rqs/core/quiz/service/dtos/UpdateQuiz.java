package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateQuiz {
    private final Member member;
    private final Long quizId;
    private final String question;
    private final List<CreateAnswer> answers;
    private final String type;
    private final String hint;

    private UpdateQuiz(Member member, Long quizId, String question, List<CreateAnswer> answers, String type, String hint) {
        this.member = member;
        this.quizId = quizId;
        this.question = question;
        this.answers = answers;
        this.type = type;
        this.hint = hint;
    }

    public static UpdateQuiz of(Member member, Long quizId, String question, List<CreateAnswer> answers, String type, String hint) {
        return new UpdateQuiz(member, quizId, question, answers, type, hint);
    }
}
