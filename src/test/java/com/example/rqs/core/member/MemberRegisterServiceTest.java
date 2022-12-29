package com.example.rqs.core.member;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.MemberRegisterServiceImpl;
import com.example.rqs.core.member.service.dtos.SignUpDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member Register Service Test")
public class MemberRegisterServiceTest {

    @InjectMocks
    MemberRegisterServiceImpl memberRegisterService;

    @Mock
    MemberRepository memberRepository;

    @Spy
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 이메일 중복 예외 테스트")
    void signUpExceptionTest() {
        SignUpDto signUpDto = new SignUpDto("이메일", "비밀번호", "터저랏");
        given(memberRepository.existsByEmail("이메일")).willReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> memberRegisterService.signUp(signUpDto));

        assertAll(
                () -> assertEquals(RQSError.DUPLICATE_EMAIL, exception.getMessage())
        );
    }
}
