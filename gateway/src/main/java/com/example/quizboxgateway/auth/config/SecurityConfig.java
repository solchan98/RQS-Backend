package com.example.quizboxgateway.auth.config;

import com.example.quizboxgateway.auth.access.AccessTokenAccessDeniedHandler;
import com.example.quizboxgateway.auth.access.AccessTokenProvider;
import com.example.quizboxgateway.auth.auth.AuthenticationFailureHandlerImpl;
import com.example.quizboxgateway.auth.auth.BearerAuthenticationConverter;
import com.example.quizboxgateway.auth.auth.SimpleAuthenticationProcessingFilter;
import com.example.quizboxgateway.auth.login.EmailPasswordAuthenticationConverter;
import com.example.quizboxgateway.auth.login.EmailPasswordAuthenticationSuccessHandler;
import com.example.quizboxgateway.auth.login.SimplePasswordEncoder;
import com.example.quizboxgateway.auth.refresh.RefreshTokenProvider;
import com.example.quizboxgateway.auth.refresh.TokenRefreshSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    private final UserDetailsService userDetailsService;

    private final AccessTokenProvider accessTokenProvider;

    private final RefreshTokenProvider refreshTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        setLoginFilter(httpSecurity);
        setAccessTokenFilter(httpSecurity);
        setTokenRefreshFilter(httpSecurity);
        setPermissions(httpSecurity);

        return httpSecurity.exceptionHandling(eh ->
                        eh.accessDeniedHandler(new AccessTokenAccessDeniedHandler(objectMapper)))
                .build();
    }

    private void setLoginFilter(HttpSecurity httpSecurity) {
        SimpleAuthenticationProcessingFilter emailPasswordAuthenticationFilter = new SimpleAuthenticationProcessingFilter(
                RequestMatchers.LOGIN,
                new EmailPasswordAuthenticationConverter(objectMapper)
        );
        emailPasswordAuthenticationFilter.setAuthenticationSuccessHandler(
                new EmailPasswordAuthenticationSuccessHandler(objectMapper, accessTokenProvider, refreshTokenProvider));
        emailPasswordAuthenticationFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl(objectMapper));

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new SimplePasswordEncoder());

        emailPasswordAuthenticationFilter.setAuthenticationManager(new ProviderManager(daoAuthenticationProvider));

        httpSecurity.addFilterBefore(emailPasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    private void setAccessTokenFilter(HttpSecurity httpSecurity) {
        AuthenticationFilter accessAuthenticationFilter = new AuthenticationFilter(
                new ProviderManager(accessTokenProvider),
                new BearerAuthenticationConverter());

        accessAuthenticationFilter.setRequestMatcher(new AndRequestMatcher(
                new NegatedRequestMatcher(RequestMatchers.PERMIT_ALL)
        ));
        accessAuthenticationFilter.setFailureHandler(new AuthenticationFailureHandlerImpl(objectMapper));
        accessAuthenticationFilter.setSuccessHandler((request, response, authentication) -> {
        });

        httpSecurity.addFilterAfter(accessAuthenticationFilter, SimpleAuthenticationProcessingFilter.class);
    }

    private void setTokenRefreshFilter(HttpSecurity httpSecurity) {
        SimpleAuthenticationProcessingFilter tokenRefreshAuthenticationFilter = new SimpleAuthenticationProcessingFilter(
                RequestMatchers.REFRESH_TOKEN,
                new BearerAuthenticationConverter()
        );
        tokenRefreshAuthenticationFilter.setAuthenticationManager(new ProviderManager(refreshTokenProvider));
        tokenRefreshAuthenticationFilter.setAuthenticationSuccessHandler(
                new TokenRefreshSuccessHandler(objectMapper, accessTokenProvider));
        tokenRefreshAuthenticationFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl(objectMapper));

        httpSecurity.addFilterBefore(tokenRefreshAuthenticationFilter, SimpleAuthenticationProcessingFilter.class);
    }

    private void setPermissions(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(a -> a.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // h2 config
                .authorizeHttpRequests(ahr -> ahr
                        .requestMatchers(RequestMatchers.PERMIT_ALL).permitAll()
                        .requestMatchers(RequestMatchers.DEFAULT).authenticated()
                );
    }
}
