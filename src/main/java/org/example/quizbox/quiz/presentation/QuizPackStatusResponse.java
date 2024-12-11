package org.example.quizbox.quiz.presentation;

import org.example.quizbox.quiz.domain.QuizPackStatus;

import java.util.Set;
import java.util.stream.Collectors;

public record QuizPackStatusResponse(
        long quizPackId,
        String quizPackTitle,
        long memberCount,
        long quizCount,
        Set<TagResponse> tags
) {

    public static QuizPackStatusResponse from(QuizPackStatus status) {
        return new QuizPackStatusResponse(
                status.quizPackId(),
                status.quizPackTitle(),
                status.quizPackMemberCount(),
                status.quizCount(),
                status.tags().getValues().stream().map(TagResponse::from).collect(Collectors.toSet())
        );
    }
}
