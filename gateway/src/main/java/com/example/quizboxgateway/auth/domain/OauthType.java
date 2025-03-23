package com.example.quizboxgateway.auth.domain;

import com.example.quizboxgateway.auth.token.AppleAuthenticationToken;
import com.example.quizboxgateway.auth.token.KakaoAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.function.Function;

public enum OauthType {
    LOCAL,
    KAKAO,
    APPLE,
    GOOGLE;

    public static final Map<OauthType, Function<String, Authentication>> createAuthentication = Map.of(
            OauthType.KAKAO, KakaoAuthenticationToken::new,
            OauthType.APPLE, AppleAuthenticationToken::new
    );
}
