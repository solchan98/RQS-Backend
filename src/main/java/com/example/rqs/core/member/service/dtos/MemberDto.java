package com.example.rqs.core.member.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.util.Objects;

@Getter
public class MemberDto {

    private final Long memberId;

    private final String email;

    private final String nickname;

    private final String description;

    private final String avatar;

    private final String role;

    public MemberDto(Long memberId, String email, String nickname, String avatar) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.description = "";
        this.avatar = avatar;
        this.role = "USER"; // TODO: 권한 체계 재구현 필요
    }

    public MemberDto(Long memberId, String email, String nickname, String description, String avatar) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.description = description;
        this.avatar = avatar;
        this.role = "USER"; // TODO: 권한 체계 재구현 필요
    }

    public static MemberDto of(Member member) {
        return new MemberDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getDescription(),
                Objects.isNull(member.getAvatar())
                        ? "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png"
                        : member.getAvatar()
        );
    }
}
