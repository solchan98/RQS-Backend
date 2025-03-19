package org.example.quizbox.quiz.presentation;

import org.example.quizbox.keyword.presentation.KeywordResponse;
import org.example.quizbox.quiz.domain.QuizPackStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record QuizPackStatusResponse(
        long quizPackId,
        String quizPackTitle,
        long memberCount,
        long quizCount,
        Set<KeywordResponse> keywords,
        LocalDateTime createdAt
) {

    public static QuizPackStatusResponse from(QuizPackStatus status) {
        return new QuizPackStatusResponse(
                status.quizPackId(),
                status.quizPackTitle(),
                status.quizPackMemberCount(),
                status.quizCount(),
                status.keywords().readonlyValues().stream().map(KeywordResponse::from).collect(Collectors.toSet()),
                status.createdAt()
        );
    }
}

