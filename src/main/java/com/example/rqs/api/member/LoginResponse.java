package com.example.rqs.api.member;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final String atk;

    public LoginResponse(String atk) {
        this.atk = atk;
    }
}
