package com.example.rqs.api.jwt;

import com.example.rqs.core.member.service.dtos.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {

    private Long memberId;

    private String email;

    private String nickname;

    private String description;

    private String avatar;

    private String role;

    private String type;

    public static Subject atk(MemberDto memberDto) {
        return new Subject(
                memberDto.getMemberId(),
                memberDto.getEmail(),
                memberDto.getNickname(),
                memberDto.getDescription(),
                memberDto.getAvatar(),
                memberDto.getRole(),
                "ATK");
    }

    public static Subject rtk(MemberDto memberDto) {
        return new Subject(
                memberDto.getMemberId(),
                memberDto.getEmail(),
                memberDto.getNickname(),
                memberDto.getDescription(),
                memberDto.getAvatar(),
                memberDto.getRole(),
                "RTK");
    }

}
