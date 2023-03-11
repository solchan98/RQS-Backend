package com.example.rqs.api.oauth;

import com.example.rqs.core.common.exception.BadRequestException;

import java.util.Arrays;

public enum OauthType {
    GOOGLE("google"),
    KAKAO("kakao");

    final String type;

    OauthType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static OauthType valueOfType(String type) {
        return Arrays
                .stream(OauthType.values()).filter((e) -> e.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Not Supported Oauth Type"));
    }
}
