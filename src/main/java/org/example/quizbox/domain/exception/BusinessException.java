package org.example.quizbox.domain.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String key) {
        super(key);
    }

    public BusinessException(String key, Throwable ex) {
        super(key, ex);
    }
}
