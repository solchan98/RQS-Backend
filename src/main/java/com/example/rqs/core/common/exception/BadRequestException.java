package com.example.rqs.core.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "요청 데이터를 확인하세요.";

    public BadRequestException() {
        super(DEFAULT_MESSAGE);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
