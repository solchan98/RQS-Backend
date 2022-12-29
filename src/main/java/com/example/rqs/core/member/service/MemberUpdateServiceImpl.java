package com.example.rqs.core.member.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.service.dtos.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberUpdateServiceImpl implements MemberUpdateService {

    @Override
    public MemberDto updateNickname(Member member, String nickname) {
        member.updateNickname(nickname);
        return MemberDto.of(member);
    }

    @Override
    public MemberDto updateDescription(Member member, String description) {
        member.updateDescription(description);
        return MemberDto.of(member);
    }

    @Override
    public MemberDto updateAvatar(Member member, String avatarUrl) {
        member.updateAvatar(avatarUrl);
        return MemberDto.of(member);
    }
}
