package com.example.rqs.core.member.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.dtos.*;
import com.example.rqs.core.common.exception.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

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
    public boolean existEmail(String email) throws BadRequestException {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public MemberDto login(LoginDto loginDto) throws BadRequestException {
        Member member = memberRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new BadRequestException(RQSError.INVALID_EMAIL_OR_PW));
        boolean matches = passwordEncoder.matches(loginDto.getPassword(), member.getPassword());
        if (!matches) throw new BadRequestException(RQSError.INVALID_EMAIL_OR_PW);
        return MemberDto.of(member);
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    @Transactional
    public MemberDto updateNickname(Member member, String updateNickname) {
        member.updateNickname(updateNickname);
        return MemberDto.of(member);
    }

    @Override
    public MemberDto updateDescription(Member member, String updateDescription) {
        return null;
    }

    @Override
    @Transactional
    public MemberDto updateAvatar(Member member, String updateUrl) {
        member.updateAvatar(updateUrl);
        return MemberDto.of(member);
    }

    @Override
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
