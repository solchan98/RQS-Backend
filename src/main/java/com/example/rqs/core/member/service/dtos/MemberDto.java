package com.example.rqs.core.member.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class MemberDto {
    private final String email;
    private final String nickname;

    private MemberDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public static MemberDto of(Member member) {
        return new MemberDto(member.getEmail(), member.getNickname());
    }
}
