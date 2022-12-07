package com.example.rqs.core.member.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.service.dtos.*;
import com.example.rqs.core.common.exception.BadRequestException;

import java.util.Optional;

public interface MemberService {

    MemberDto signUp(SignUpDto signUpDto) throws BadRequestException;

    boolean existEmail(String email);

    MemberDto login(LoginDto loginDto) throws BadRequestException;

    boolean logout(); // 실패 시, 예외 혹은 불리언 아직 미정

    MemberDto updateNickname(Member member, String updateNickname);

    MemberDto updateDescription(Member member, String updateDescription);

    MemberDto updateAvatar(Member member, String updateUrl);

    Optional<Member> getMemberById(Long memberId);

    Optional<Member> getMemberByEmail(String email);
}
