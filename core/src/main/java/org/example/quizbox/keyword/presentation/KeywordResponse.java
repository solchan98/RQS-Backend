package org.example.quizbox.keyword.presentation;

import org.example.quizbox.keyword.domain.Keyword;

public record KeywordResponse(Long id, String name) {

    public static KeywordResponse from(Keyword keyword) {
        return new KeywordResponse(keyword.getId(), keyword.getName());
    }
}
