package com.example.autoquizbox.infrastructure.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private HttpStatus status;
    private String message;
    private Object data;
}
