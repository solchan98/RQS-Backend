package com.example.rqs.core.member.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.dtos.*;
import com.example.rqs.core.common.exception.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MemberDto signUp(SignUpDto signUpDto) throws BadRequestException {
        boolean exist = memberRepository.existsByEmail(signUpDto.getEmail());
        if (exist) throw new BadRequestException(RQSError.DUPLICATE_EMAIL);
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        Member member = Member.newMember(signUpDto.getEmail(), encodedPassword, signUpDto.getNickname());
        memberRepository.save(member);
        return MemberDto.of(member);
    }

    @Override
    public MemberDto login() {
        return null;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public MemberDto updateMember() {
        return null;
    }
}
