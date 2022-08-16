package com.example.rqs.api.member;

import com.example.rqs.core.member.service.dtos.MemberDto;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final Long memberId;
    private final String email;
    private final String nickname;
    private final String atk;

    private LoginResponse(Long memberId, String email, String nickname, String atk) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.atk = atk;
    }

    public static LoginResponse of(Long memberId, String email, String nickname, String atk) {
        return new LoginResponse(memberId, email, nickname, atk);
    }

    public static LoginResponse of(MemberDto memberDto, String atk) {
        return new LoginResponse(
                memberDto.getMemberId(),
                memberDto.getEmail(),
                memberDto.getNickname(),
                atk);
    }
}
