package org.example.quizbox.quiz.presentation;

import org.example.quizbox.quiz.domain.QuizPackStatus;
import org.example.quizbox.tag.domain.Tag;

import java.util.Set;
import java.util.stream.Collectors;

public record QuizPackStatusResponse(
        long quizPackId,
        String quizPackTitle,
        long memberCount,
        long quizCount,
        Set<String> tags
) {

    public static QuizPackStatusResponse from(QuizPackStatus status) {
        return new QuizPackStatusResponse(
                status.quizPackId(),
                status.quizPackTitle(),
                status.quizPackMemberCount(),
                status.quizCount(),
                status.tags().getValues().stream().map(Tag::getName).collect(Collectors.toSet())
        );
    }
}
