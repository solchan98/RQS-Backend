package com.example.rqs.core.member.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.service.dtos.MemberDto;

public interface MemberUpdateService {
    MemberDto updateNickname(Member member, String nickname);
    MemberDto updateDescription(Member member, String description);
    MemberDto updateAvatar(Member member, String avatarUrl);
}
