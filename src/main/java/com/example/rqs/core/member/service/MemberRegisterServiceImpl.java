package com.example.rqs.core.member.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.dtos.MemberDto;
import com.example.rqs.core.member.service.dtos.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRegisterServiceImpl implements MemberRegisterService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MemberDto signUp(SignUpDto signUpDto) {
        boolean exist = memberRepository.existsByEmail(signUpDto.getEmail());
        if (exist) throw new BadRequestException(RQSError.DUPLICATE_EMAIL);
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        Member member = Member.newMember(signUpDto.getEmail(), encodedPassword, signUpDto.getNickname());
        memberRepository.save(member);
        return MemberDto.of(member);
    }
}
