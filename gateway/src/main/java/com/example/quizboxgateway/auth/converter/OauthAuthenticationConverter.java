package com.example.quizboxgateway.auth.converter;

import com.example.quizboxgateway.auth.domain.OauthType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

import static com.example.quizboxgateway.auth.domain.OauthType.createAuthentication;

public class OauthAuthenticationConverter implements AuthenticationConverter {
    private static final String AUTHENTICATION_SCHEME = "BEARER";

    @Override
    public Authentication convert(HttpServletRequest request) {
        // TODO: oauth type 추출 로직 필요, 이때 없는 케이스면 '지원하지 않은 로그인 방식' 에러 응답
        OauthType oauthType = OauthType.KAKAO;

        String header = request.getHeader("Authorization");
        if (header == null || header.isBlank()) {
            throw new BadCredentialsException("인증 정보를 확인하세요.");
        }

        header = header.replace(" ", "");
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME)) {
            throw new BadCredentialsException("인증 정보를 확인하세요.");
        }

        String token = header.substring(AUTHENTICATION_SCHEME.length());
        return createAuthentication.get(oauthType).apply(token);
    }
}
