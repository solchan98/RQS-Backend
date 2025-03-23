package com.example.quizboxgateway.auth.successhandler;

import com.example.quizboxgateway.auth.provider.AccessTokenProvider;
import com.example.quizboxgateway.auth.domain.AccessUser;
import com.example.quizboxgateway.auth.domain.AuthUserDetails;
import com.example.quizboxgateway.auth.provider.RefreshTokenProvider;
import com.example.quizboxgateway.common.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final AccessTokenProvider accessTokenProvider;

    private final RefreshTokenProvider refreshTokenProvider;

    record LoginResponse(String accessToken, String refreshToken) {
        public LoginResponse(Authentication accessToken, Authentication refreshToken) {
            this(accessToken.getName(), refreshToken.getName());
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        try {
            AccessUser authenticated = AccessUser.authenticated((AuthUserDetails) authentication.getPrincipal());

            Authentication accessToken = accessTokenProvider.createToken(authenticated);
            Authentication refreshToken = refreshTokenProvider.createToken(authenticated.getEmail());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            objectMapper.writeValue(
                    response.getWriter(),
                    new CommonResponse<>(
                            HttpStatus.OK.name(),
                            "로그인에 성공하였습니다.",
                            new LoginResponse(accessToken, refreshToken))
            );
        } catch (IOException e) {
            throw new RuntimeException("서버에서 문제가 발생하였습니다.", e);
        }
    }
}
