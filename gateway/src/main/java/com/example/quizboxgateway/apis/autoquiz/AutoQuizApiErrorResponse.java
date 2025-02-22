package com.example.quizboxgateway.apis.autoquiz;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class AutoQuizApiErrorResponse {

    private final String errorKey;
    private final String message;
    private final Object data;

    @JsonCreator
    public AutoQuizApiErrorResponse(String errorKey, String message, Object data) {
        this.errorKey = errorKey;
        this.message = message;
        this.data = data;
    }
}
