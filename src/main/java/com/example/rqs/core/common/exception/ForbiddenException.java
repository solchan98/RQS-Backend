package com.example.rqs.core.common.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "권한이 존재하지 않습니다.";

    public ForbiddenException() {
        super(DEFAULT_MESSAGE);
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
