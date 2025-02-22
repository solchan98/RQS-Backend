package com.example.autoquizbox.common;

import com.example.autoquizbox.infrastructure.core.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Object>> businessException(BusinessException exception) {
        BodyBuilder bodyBuilder = ResponseEntity.status(exception.getStatus());

        return bodyBuilder.body(new CommonResponse<>(
                        HttpStatus.valueOf(exception.getStatus()).name(),
                        exception.getMessage(),
                        null
                )
        );
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<CommonResponse<Object>> ServiceUnavailableException(ServiceUnavailableException exception) {
        BodyBuilder bodyBuilder = ResponseEntity.status(503);

        return bodyBuilder.body(new CommonResponse<>(
                        HttpStatus.SERVICE_UNAVAILABLE.name(),
                        "서버 작업중",
                        null
                )
        );
    }

    @ExceptionHandler({ApiException.class, ConnectException.class})
    public ResponseEntity<CommonResponse<Object>> handleApiException(Exception exception) {
        if (exception instanceof ApiException apiException) {
            return ResponseEntity.status(apiException.getStatus())
                    .body(new CommonResponse<>(
                                    apiException.getStatus().name(),
                                    apiException.getMessage(),
                                    apiException.getData()
                            )
                    );
        }

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new CommonResponse<>(
                                HttpStatus.SERVICE_UNAVAILABLE.name(),
                                "서버 작업중",
                                null
                        )
                );
    }

}
