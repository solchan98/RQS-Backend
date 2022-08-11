package com.example.rqs.core.member.service;

import com.example.rqs.core.member.service.dtos.MemberDto;
import com.example.rqs.core.member.service.dtos.SignUpDto;
import com.example.rqs.core.common.exception.BadRequestException;

public interface MemberService {

    MemberDto signUp(SignUpDto signUpDto) throws BadRequestException;

    MemberDto login();

    boolean logout(); // 실패 시, 예외 혹은 불리언 아직 미정

    MemberDto updateMember();
}
