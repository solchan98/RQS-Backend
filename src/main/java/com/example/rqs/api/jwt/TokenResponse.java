package com.example.rqs.api.jwt;

import lombok.Getter;

@Getter
public class TokenResponse {

    private final String atk;

    private final String rtk;

    private TokenResponse(String atk, String rtk) {
        this.atk = atk;
        this.rtk = rtk;
    }

    public static TokenResponse of (String atk, String rtk) {
        return new TokenResponse(atk, rtk);
    }
}
