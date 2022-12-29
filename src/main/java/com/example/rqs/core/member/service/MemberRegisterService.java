package com.example.rqs.core.member.service;

import com.example.rqs.core.member.service.dtos.MemberDto;
import com.example.rqs.core.member.service.dtos.SignUpDto;

public interface MemberRegisterService {
    MemberDto signUp(SignUpDto signUpDto);
}
