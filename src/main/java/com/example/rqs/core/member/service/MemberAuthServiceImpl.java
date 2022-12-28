package com.example.rqs.core.member.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.dtos.LoginDto;
import com.example.rqs.core.member.service.dtos.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAuthServiceImpl implements MemberAuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto login(LoginDto loginDto) {
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
    public boolean existEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Optional<Member> getMember(long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> getMember(String email) {
        return memberRepository.findByEmail(email);
    }
}
