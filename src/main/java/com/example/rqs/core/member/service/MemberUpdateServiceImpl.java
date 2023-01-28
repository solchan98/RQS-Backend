package com.example.rqs.core.member.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.dtos.MemberDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberUpdateServiceImpl implements MemberUpdateService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDto updateMember(Member member, String nickname, String description) {
        member.updateNickname(nickname);
        member.updateDescription(description);
        memberRepository.save(member);
        return MemberDto.of(member);
    }

    @Override
    public MemberDto updateAvatar(Member member, String avatarUrl) {
        member.updateAvatar(avatarUrl);
        memberRepository.save(member);
        return MemberDto.of(member);
    }
}
