package com.example.rqs.api.jwt;

import com.example.rqs.api.RedisDao;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("jwtProvider 테스트")
public class JwtProviderImplTest {

    @InjectMocks
    private JwtProviderImpl jwtProviderImpl;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private RedisDao redisDao;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtProviderImpl, "key", "secret");
        ReflectionTestUtils.setField(jwtProviderImpl, "atkLive", 6000L);
        ReflectionTestUtils.setField(jwtProviderImpl, "rtkLive", 6000L * 30);
    }

    @Test
    @DisplayName("jwtProvider 토큰 생성 테스트")
    void jwtCreateTokenTest() {
        TokenResponse tokenList = jwtProviderImpl.createTokenList("sol@sol.com", "sol", "USER");
        assertAll(
                () -> assertThat(tokenList.getAtk()).isNotEmpty(),
                () -> assertThat(tokenList.getRtk()).isNotEmpty()
        );
    }

    @Test
    @DisplayName("jwtProvider 토큰 Payload Subject 확인 테스트")
    void jwtPayloadSubjectTest() throws JsonProcessingException {
        TokenResponse tokenList = jwtProviderImpl.createTokenList("sol@sol.com", "sol", "USER");
        Subject subject = jwtProviderImpl.getSubject(tokenList.getAtk());
        assertAll(
                () -> assertThat(subject.getEmail()).isEqualTo("sol@sol.com"),
                () -> assertThat(subject.getNickname()).isEqualTo("sol"),
                () -> assertThat(subject.getRole()).isEqualTo("USER")
        );
    }

    @Test
    @DisplayName("jwtProvider 로그아웃 된 rtk로 reissue 예외 테스트")
    void reissueTestByExpiredRTK() {
        given(redisDao.getValues("sol@sol.com")).willReturn(null);

        ForbiddenException exception = assertThrows(
                ForbiddenException.class,
                () -> jwtProviderImpl.reissueAtk("sol@sol.com", "sol", "USER"));

        assertAll(
                () -> assertEquals("인증 정보가 만료되었습니다.", exception.getMessage())
        );
    }
}
