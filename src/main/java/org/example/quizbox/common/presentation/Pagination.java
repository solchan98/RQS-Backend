package org.example.quizbox.common.presentation;

public record Pagination(
        Long lastId,
        int chunk,
        boolean finish
) {}
