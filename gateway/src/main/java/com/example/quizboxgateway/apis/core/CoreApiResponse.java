package com.example.quizboxgateway.apis.core;

import lombok.Getter;

@Getter
public class CoreApiResponse<T> {

    private String status;
    private String message;
    private T data;

    public CoreApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
