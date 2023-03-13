package com.example.rqs.api.member;

import lombok.Getter;

@Getter
public class CheckEmailResponse {
    private final String email;
    private final boolean exist;

    public CheckEmailResponse(String email, boolean exist) {
        this.email = email;
        this.exist = exist;
    }

    public static CheckEmailResponse of(String email, boolean exist) {
        return new CheckEmailResponse(email, exist);
    }
}
