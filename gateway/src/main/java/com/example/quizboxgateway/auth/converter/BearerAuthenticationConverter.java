package com.example.quizboxgateway.auth.converter;

import com.example.quizboxgateway.auth.domain.OauthType;
import com.example.quizboxgateway.auth.token.AppleAuthenticationToken;
import com.example.quizboxgateway.auth.token.BearerAuthenticationToken;
import com.example.quizboxgateway.auth.token.KakaoAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.function.Function;

public class BearerAuthenticationConverter implements AuthenticationConverter {

    private static final String AUTHENTICATION_SCHEME = "BEARER";

    @Override
    public Authentication convert(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || header.isBlank()) {
            throw new BadCredentialsException("인증 정보를 확인하세요.");
        }

        header = header.replace(" ", "");
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME)) {
            throw new BadCredentialsException("인증 정보를 확인하세요.");
        }

        return new BearerAuthenticationToken(header.substring(AUTHENTICATION_SCHEME.length()), false);
    }
}
