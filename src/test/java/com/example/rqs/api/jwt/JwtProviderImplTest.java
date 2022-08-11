package com.example.rqs.api.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("jwtProvider 테스트")
public class JwtProviderImplTest {

    @InjectMocks
    private JwtProviderImpl jwtProviderImpl;

    @Spy
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtProviderImpl, "key", "secret");
        ReflectionTestUtils.setField(jwtProviderImpl, "atkLive", 6000L);
    }

    @Test
    @DisplayName("jwtProvider 토큰 생성 테스트")
    void jwtCreateTokenTest() {
        String atk = jwtProviderImpl.createAccessToken("sol@sol.com", "sol", "USER");
        assertThat(atk).isNotEmpty();
    }

    @Test
    @DisplayName("jwtProvider 토큰 Payload Subject 확인 테스트")
    void jwtPayloadSubjectTest() throws JsonProcessingException {
        String atk = jwtProviderImpl.createAccessToken("sol@sol.com", "sol", "USER");
        Subject subject = jwtProviderImpl.getSubject(atk);
        assertAll(
                () -> assertThat(subject.getEmail()).isEqualTo("sol@sol.com"),
                () -> assertThat(subject.getNickname()).isEqualTo("sol"),
                () -> assertThat(subject.getRole()).isEqualTo("USER")
        );
    }
}
