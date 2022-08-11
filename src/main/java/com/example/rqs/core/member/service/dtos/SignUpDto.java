package com.example.rqs.core.member.service.dtos;

import lombok.Getter;

@Getter
public class SignUpDto {
    private final String email;
    private final String password;
    private final String nickname;

    public SignUpDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
