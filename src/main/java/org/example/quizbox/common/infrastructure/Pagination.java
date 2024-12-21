package org.example.quizbox.common.infrastructure;

public record Pagination(
        Long lastId,
        int chunk,
        boolean finish
) {}
