package com.example.rqs.api.jwt;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.service.MemberService;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberService memberService;

    public MemberDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService
                .getMemberByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new MemberDetails(member);
    }
}
