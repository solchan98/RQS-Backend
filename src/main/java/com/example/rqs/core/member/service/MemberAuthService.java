package com.example.rqs.core.member.service;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.service.dtos.LoginDto;
import com.example.rqs.core.member.service.dtos.MemberDto;

import java.util.Optional;

public interface MemberAuthService {
    MemberDto login(LoginDto loginDto);
    boolean logout();
    boolean existEmail(String email);
    Optional<Member> getMember(long memberId);
    Optional<Member> getMember(String memberId);

}
