package com.example.rqs.core.member.service.dtos;

import lombok.Getter;

@Getter
public class LoginDto {

    private final String email;

    private final String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
