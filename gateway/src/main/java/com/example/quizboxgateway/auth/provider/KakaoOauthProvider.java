package com.example.quizboxgateway.auth.provider;

import com.example.quizboxgateway.auth.domain.AuthUserDetails;
import com.example.quizboxgateway.auth.domain.OauthService;
import com.example.quizboxgateway.auth.domain.OauthType;
import com.example.quizboxgateway.auth.infrastructure.oauth.kakao.KakaoOauthApi;
import com.example.quizboxgateway.auth.token.BearerAuthenticationToken;
import com.example.quizboxgateway.auth.token.KakaoAuthenticationToken;
import com.example.quizboxgateway.common.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class KakaoOauthProvider implements OauthProvider {

    private final KakaoOauthApi kakaoOauthApi;
    private final OauthService oauthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KakaoAuthenticationToken token = (KakaoAuthenticationToken) authentication;
        try {
            String oauthId = kakaoOauthApi.getAccountByAccessToken(token.getName()).getId();
            AuthUserDetails authUserDetails = oauthService.signIn(OauthType.KAKAO, oauthId);

            return UsernamePasswordAuthenticationToken
                    .authenticated(authUserDetails, authUserDetails, authentication.getAuthorities());
        } catch (ApiException e) {
            throw new BadCredentialsException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return KakaoAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

/**
 * {
 * "aud": "cc1dfb0ff3ba6fc45a04a2771433fd75",
 * "sub": "2702128167",
 * "auth_time": 1742465494,
 * "iss": "https://kauth.kakao.com",
 * "nickname": "박솔찬",
 * "exp": 1742487094,
 * "iat": 1742465494,
 * "picture": "https://k.kakaocdn.net/dn/ixAB2/btsMvekO4FG/nxvbotUYfRFmTW8h23SNt1/img_110x110.jpg"
 * }
 */
