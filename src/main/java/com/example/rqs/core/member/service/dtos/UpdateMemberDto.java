package com.example.rqs.core.member.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class UpdateMemberDto {

    private final Member member;

    private final String nickname;

    private UpdateMemberDto(Member member, String nickname) {
        this.member = member;
        this.nickname = nickname;
    }

    public static UpdateMemberDto of(Member member, String nickname) {
        return new UpdateMemberDto(member, nickname);
    }
}
