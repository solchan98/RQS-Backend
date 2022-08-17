package com.example.rqs.api.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.JwtException;

public interface JwtProvider {
    TokenResponse createTokenList(String email, String nickname, String role);

    String reissueAtk(String email, String nickname, String role);
    Subject getSubject(String atk) throws JwtException, JsonProcessingException;
}
