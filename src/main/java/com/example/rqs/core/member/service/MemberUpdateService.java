package com.example.rqs.core.member.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.service.dtos.MemberDto;

public interface MemberUpdateService {

    MemberDto updateMember(Member member, String nickname, String description);
    MemberDto updateAvatar(Member member, String avatarUrl);
}
