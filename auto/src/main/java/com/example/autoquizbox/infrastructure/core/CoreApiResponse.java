package com.example.autoquizbox.infrastructure.core;

import lombok.Getter;

@Getter
public class CoreApiResponse<T> {

    private T data;

    public CoreApiResponse(T data) {
        this.data = data;
    }
}
