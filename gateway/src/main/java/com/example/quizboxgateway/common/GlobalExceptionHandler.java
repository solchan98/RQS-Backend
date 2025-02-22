package com.example.quizboxgateway.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GatewayException.class)
    public ResponseEntity<CommonResponse<Object>> handleGatewayException(GatewayException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new CommonResponse<>(
                                HttpStatus.BAD_GATEWAY.name(),
                                exception.getMessage(),
                                null
                        )
                );
    }

    /**
     * TODO
     *  디테일한 핸들링 필요
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Object>> handleBusinessException(BusinessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CommonResponse<>(
                                HttpStatus.BAD_REQUEST.name(),
                                exception.getMessage(),
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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CommonResponse<>(
                                HttpStatus.BAD_GATEWAY.name(),
                                "서버 작업중", // TODO: 어떤 에러 메세지를??
                                null
                        )
                );
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<CommonResponse<Object>> handleServerException(ServerException exception) {
        // TODO: 원인 분석 및 대응이 필요한 에러
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new CommonResponse<>(
                                HttpStatus.SERVICE_UNAVAILABLE.name(),
                                "서버가 원활하지 않습니다.",
                                null
                        )
                );
    }
}
