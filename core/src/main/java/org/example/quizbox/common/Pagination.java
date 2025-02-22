package org.example.quizbox.common;

public record Pagination(
        Long lastId,
        int chunk,
        boolean finish
) {}
