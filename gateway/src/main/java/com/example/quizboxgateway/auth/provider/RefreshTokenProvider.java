
package com.example.quizboxgateway.auth.provider;

import com.example.quizboxgateway.auth.domain.AccessUser;
import com.example.quizboxgateway.auth.domain.AuthUserDetails;
import com.example.quizboxgateway.auth.domain.RefreshTokenRepository;
import com.example.quizboxgateway.auth.token.BearerAuthenticationToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component(value = "refreshTokenProvider")
@RequiredArgsConstructor
public class RefreshTokenProvider implements AuthenticationProvider {
    private static final String TOKEN_SUBJECT = "refresh token";

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Claims claims = tokenProvider.verify(authentication.getName(), TOKEN_SUBJECT);

        String email = claims.get("email", String.class);
        AuthUserDetails authUserDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(email);

        boolean empty = refreshTokenRepository.findByEmail(email)
                .filter(token -> Objects.equals(token, authentication.getName()))
                .stream()
                .findAny()
                .isEmpty();

        if (empty) {
            throw new BadCredentialsException("인증 정보를 확인하세요.");
        }

        return AccessUser.authenticated(authUserDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (BearerAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public BearerAuthenticationToken createToken(String email) {
        long tokenLive = 1000L * 60L * 60L; // 1h
        BearerAuthenticationToken token = tokenProvider.createToken(TOKEN_SUBJECT, Map.of("email", email), tokenLive);
        refreshTokenRepository.save(email, token.getName(), tokenLive);

        return token;
    }
}
