package com.example.rqs.api.exception;

import com.example.rqs.core.common.exception.BadRequestException;

import com.example.rqs.core.common.exception.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RQSExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Message> handle(BadRequestException e) {
        Message message = new Message(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Message> handle(ForbiddenException e) {
        Message message = new Message(e.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
