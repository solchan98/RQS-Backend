package org.example.quizbox.common.presentation;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(exception.getMessage(), exception.getExceptionConstance().detail()));
    }

    public record ErrorResponse(String errorKey, String message) {

    }
}
