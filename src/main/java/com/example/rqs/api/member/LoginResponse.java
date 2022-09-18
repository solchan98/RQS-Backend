package com.example.rqs.api.member;

import com.example.rqs.api.jwt.TokenResponse;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final TokenResponse tokenObj;

    private LoginResponse(TokenResponse tokenObj) {
        this.tokenObj = tokenObj;
    }

    public static LoginResponse of(TokenResponse tokenObj) {
        return new LoginResponse(tokenObj);
    }
}
