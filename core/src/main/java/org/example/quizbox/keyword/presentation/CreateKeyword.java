package org.example.quizbox.keyword.presentation;

import org.example.quizbox.keyword.domain.Keyword;

public record CreateKeyword(
        String name
) {

    public Keyword toKeyword() {
        return new Keyword(null, name);
    }

}
