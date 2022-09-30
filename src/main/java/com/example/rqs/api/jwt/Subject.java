package com.example.rqs.api.jwt;

import com.example.rqs.core.member.service.dtos.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {

    private Long memberId;

    private String email;

    private String nickname;

    private String role;

    private String type;

    public static Subject atk(MemberDto memberDto) {
        return new Subject(
                memberDto.getMemberId(),
                memberDto.getEmail(),
                memberDto.getNickname(),
                memberDto.getRole(),
                "ATK");
    }

    public static Subject rtk(MemberDto memberDto) {
        return new Subject(
                memberDto.getMemberId(),
                memberDto.getEmail(),
                memberDto.getNickname(),
                memberDto.getRole(),
                "RTK");
    }

}
