package com.example.rqs.api.member;

import com.example.rqs.api.jwt.TokenResponse;
import com.example.rqs.core.member.service.dtos.MemberDto;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final Long memberId;
    private final String email;
    private final String nickname;
    private final TokenResponse tokenObj;

    private LoginResponse(Long memberId, String email, String nickname, TokenResponse tokenObj) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.tokenObj = tokenObj;
    }

    public static LoginResponse of(Long memberId, String email, String nickname, TokenResponse tokenObj) {
        return new LoginResponse(memberId, email, nickname, tokenObj);
    }

    public static LoginResponse of(MemberDto memberDto, TokenResponse tokenObj) {
        return new LoginResponse(
                memberDto.getMemberId(),
                memberDto.getEmail(),
                memberDto.getNickname(),
                tokenObj);
    }
}
