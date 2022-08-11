package com.example.rqs.api.jwt;


public interface JwtProvider {
    String createAccessToken(String email, String nickname, String role);
    Subject getSubject(String atk);
}
