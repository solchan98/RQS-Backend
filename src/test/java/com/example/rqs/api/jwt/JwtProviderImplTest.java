package com.example.rqs.api.jwt;

import com.example.rqs.core.common.redis.RedisDao;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.member.service.dtos.MemberDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

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
        ReflectionTestUtils.setField(jwtProviderImpl, "itkLive", 30000L);
    }

    @AfterEach
    void clearCache() {
        Set<String> keySet = redisDao.getKeys("*");
        keySet.forEach(redisDao::deleteValues);
    }

    @Test
    @DisplayName("jwtProvider 토큰 생성 테스트")
    void jwtCreateTokenTest() {
        TokenResponse tokenList = jwtProviderImpl.createTokenList(new MemberDto(1L, "sol@sol.com", "sol", ""));
        assertAll(
                () -> assertThat(tokenList.getAtk()).isNotEmpty(),
                () -> assertThat(tokenList.getRtk()).isNotEmpty()
        );
    }

    @Test
    @DisplayName("jwtProvider 토큰 Payload Subject 확인 테스트")
    void jwtPayloadSubjectTest() throws JsonProcessingException {
        TokenResponse tokenList = jwtProviderImpl.createTokenList(new MemberDto(1L, "sol@sol.com", "sol", ""));
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
                () -> jwtProviderImpl.reissueAtk(new MemberDto(1L, "sol@sol.com", "sol", "")));

        assertAll(
                () -> assertEquals("인증 정보가 만료되었습니다.", exception.getMessage())
        );
    }

    @Test
    @DisplayName("jwtProvider inviteToken 생성 테스트")
    void createInviteToken() {
        Long spaceId = 1L; String spaceTitle = "테스트 스페이스";
        Long inviterId = 1L; String inviterNickname = "sol";
        InviteSpaceSubject inviteSpaceSubject = InviteSpaceSubject.of(
                spaceId,
                spaceTitle,
                inviterId,
                inviterNickname);
        InviteSpaceTokenResponse inviteToken = jwtProviderImpl.createInviteToken(inviteSpaceSubject);
        String itk = inviteToken.getInviteToken();

        assertAll(
                () -> assertThat(itk).isNotNull()
        );
    }

    @Test
    @DisplayName("jwtProvider inviteTokenSubject 확인 테스트")
    void itkPayloadSubjectTest() throws JsonProcessingException {
        Long spaceId = 1L; String spaceTitle = "테스트 스페이스";
        Long inviterId = 1L; String inviterNickname = "sol";
        InviteSpaceSubject inviteSpaceSubject = InviteSpaceSubject.of(
                spaceId,
                spaceTitle,
                inviterId,
                inviterNickname);
        InviteSpaceTokenResponse inviteToken = jwtProviderImpl.createInviteToken(inviteSpaceSubject);
        InviteSpaceSubject subject = jwtProviderImpl.getInviteSpaceSubject(inviteToken.getInviteToken());

        assertAll(
                () -> assertThat(subject.getSpaceId()).isEqualTo(1L),
                () -> assertThat(subject.getSpaceTitle()).isEqualTo(spaceTitle),
                () -> assertThat(subject.getInviterId()).isEqualTo(1L),
                () -> assertThat(subject.getInviterNickname()).isEqualTo(inviterNickname)
        );
    }
}
