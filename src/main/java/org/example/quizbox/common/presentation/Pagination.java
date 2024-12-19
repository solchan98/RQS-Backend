package org.example.quizbox.common.presentation;

public record Pagination(
        long lastId,
        int chunk,
        boolean finish
) {}
