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
    public MemberDto updateNickname(Member member, String nickname) {
        member.updateNickname(nickname);
        memberRepository.save(member);
        return MemberDto.of(member);
    }

    @Override
    public MemberDto updateDescription(Member member, String description) {
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
