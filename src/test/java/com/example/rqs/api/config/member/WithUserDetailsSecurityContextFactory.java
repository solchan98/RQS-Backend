package com.example.rqs.api.config.member;

import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.member.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithUserDetailsSecurityContextFactory implements WithSecurityContextFactory<TestMember> {
    @Override
    public SecurityContext createSecurityContext(TestMember annotation) {
        Member member = Member.newMember("sol@sol.com", "password", "sol");
        UserDetails userAccount = new MemberDetails(member);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userAccount, "", userAccount.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        return context;
    }
}