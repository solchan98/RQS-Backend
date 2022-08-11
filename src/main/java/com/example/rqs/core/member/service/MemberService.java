package com.example.rqs.core.member.service;
public interface MemberService {

    MemberDto signUp(SignUpDto signUpDto);

    MemberDto login();

    boolean logout(); // 실패 시, 예외 혹은 불리언 아직 미정

    MemberDto updateMember();
}
