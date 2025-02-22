package org.example.quizbox.common;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        BodyBuilder bodyBuilder = ResponseEntity.internalServerError();

        if ("BUSINESS".equals(exception.getExceptionConstance().type())) {
            bodyBuilder = ResponseEntity.badRequest();
        }

        if ("AUTHORIZATION".equals(exception.getExceptionConstance().type())) {
            bodyBuilder = ResponseEntity.status(403);
        }

        return bodyBuilder.body(new ErrorResponse(exception.getMessage(), exception.getExceptionConstance().detail()));
    }

    public record ErrorResponse(String errorKey, String message) {

    }
}
