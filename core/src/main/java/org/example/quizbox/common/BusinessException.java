package org.example.quizbox.common;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ExceptionConstants exceptionConstance;

    public BusinessException(ExceptionConstants exceptionConstance) {
        super(exceptionConstance.code());
        this.exceptionConstance = exceptionConstance;
    }

    public BusinessException(ExceptionConstants exceptionConstance, Throwable ex) {
        super(exceptionConstance.code(), ex);
        this.exceptionConstance = exceptionConstance;
    }
}
