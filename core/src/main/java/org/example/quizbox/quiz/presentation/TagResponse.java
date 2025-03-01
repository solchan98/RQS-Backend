package org.example.quizbox.quiz.presentation;

import org.example.quizbox.keyword.domain.Keyword;

public record TagResponse(
        long id,
        String name
) {
    public static TagResponse from(Keyword keyword) {
        return new TagResponse(keyword.getId(), keyword.getName());

    }
}
