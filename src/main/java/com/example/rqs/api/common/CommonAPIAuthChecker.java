package com.example.rqs.api.common;

import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.api.jwt.Subject;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.member.service.MemberAuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommonAPIAuthChecker {

    private final MemberAuthService memberAuthService;
    private final JwtProvider jwtProvider;

    public MemberDetails checkIsAuth(String authorization) {
        try {
            Subject subject = jwtProvider.getSubject(authorization.substring(7));
            return new MemberDetails(
                    memberAuthService.getMember(subject.getMemberId()).orElseThrow(BadRequestException::new)
            );
        } catch (Exception e) {
            return null;
        }
    }
}
