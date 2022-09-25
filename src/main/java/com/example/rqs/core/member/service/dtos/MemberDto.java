package com.example.rqs.core.member.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.util.Objects;

@Getter
public class MemberDto {

    private final Long memberId;

    private final String email;

    private final String nickname;

    private final String avatar;

    private final String role;


    public MemberDto(Long memberId, String email, String nickname, String avatar) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.avatar = avatar;
        this.role = "USER"; // TODO: 권한 체계 재구현 필요
    }

    public static MemberDto of(Member member) {
        return new MemberDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                Objects.isNull(member.getAvatar()) ? "" : member.getAvatar().getUrl()
                // TODO: avatar가 없는 경우, 기본 이미지 경로 첨부하도록 수정 예정
        );
    }
}
