package com.example.rqs.core.member.service;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Override
    public MemberDto signUp(SignUpDto signUpDto) {
        return null;
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
