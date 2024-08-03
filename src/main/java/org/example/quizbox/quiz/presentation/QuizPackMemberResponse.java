package org.example.quizbox.quiz.presentation;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.example.quizbox.quiz.domain.QuizPackMemberRole;

public record QuizPackMemberResponse(
        long memberId,
        long quizPackMemberId,
        Set<String> roles
) {

    public static QuizPackMemberResponse from(QuizPackMember quizPackMember) {
        return new QuizPackMemberResponse(
                quizPackMember.getMemberId(),
                quizPackMember.getQuizPackMemberId(),
                quizPackMember.getRoles().stream().map(QuizPackMemberRole::name).collect(Collectors.toSet())
        );
    }

}
