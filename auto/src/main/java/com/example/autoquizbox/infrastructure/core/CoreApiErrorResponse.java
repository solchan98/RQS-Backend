package com.example.autoquizbox.infrastructure.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class CoreApiErrorResponse {

    private final String errorKey;
    private final String message;
    private final Object data;

    @JsonCreator
    public CoreApiErrorResponse(String errorKey, String message, Object data) {
        this.errorKey = errorKey;
        this.message = message;
        this.data = data;
    }
}
