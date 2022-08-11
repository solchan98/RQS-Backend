package com.example.rqs.api.config;

import com.example.rqs.api.jwt.JwtAuthenticationFilter;
import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.api.jwt.MemberDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApiConfig {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public ApiConfig(JwtProvider jwtProvider, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, MemberDetailsService memberDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.memberDetailsService = memberDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/api/v1/member/sign-up", "/api/v1/member/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, memberDetailsService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
