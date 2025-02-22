package com.example.quizboxgateway.apis.autoquiz;

import lombok.Getter;

@Getter
public class AutoQuizApiResponse<T> {

    private String status;
    private String message;
    private T data;

    public AutoQuizApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
