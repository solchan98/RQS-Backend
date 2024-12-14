package org.example.quizbox.common.presentation;

public record PaginationResponse<T>(
        Pagination pagination,
        T data
) {

}
