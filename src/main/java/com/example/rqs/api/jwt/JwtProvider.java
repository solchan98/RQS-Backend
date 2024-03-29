package com.example.rqs.api.jwt;

import com.example.rqs.core.member.service.dtos.MemberDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.JwtException;

public interface JwtProvider {
    TokenResponse createTokensByLogin(MemberDto memberDto);
    String reissueAtk(MemberDto memberDto);
    Subject getSubject(String atk) throws JwtException, JsonProcessingException;
    byte[] decode(String token);
}
