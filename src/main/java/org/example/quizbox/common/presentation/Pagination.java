package org.example.quizbox.common.presentation;

public record Pagination(
        long lastId,
        long chunk,
        boolean finish
) {}
