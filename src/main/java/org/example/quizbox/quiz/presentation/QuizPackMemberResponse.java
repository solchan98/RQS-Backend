package org.example.quizbox.quiz.presentation;

import org.example.quizbox.quiz.domain.QuizPackMember;

public record QuizPackMemberResponse(
        long memberId,
        long quizPackMemberId,
        String role
) {

    public static QuizPackMemberResponse from(QuizPackMember quizPackMember) {
        return new QuizPackMemberResponse(
                quizPackMember.getMemberId(),
                quizPackMember.getQuizPackMemberId(),
                quizPackMember.getRole().name()
        );
    }

}
