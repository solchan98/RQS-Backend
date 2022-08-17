package com.example.rqs.core.member;


import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.MemberServiceImpl;
import com.example.rqs.core.member.service.dtos.LoginDto;
import com.example.rqs.core.member.service.dtos.SignUpDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 서비스 테스트")
public class MemberServiceTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberRepository memberRepository;

    @Spy
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 이메일 중복 예외 테스트")
    void signUpExceptionTest() {
        SignUpDto signUpDto = new SignUpDto("이메일", "비밀번호", "터저랏");
        given(memberRepository.existsByEmail("이메일")).willReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> memberService.signUp(signUpDto));

        assertAll(
                () -> assertEquals(RQSError.DUPLICATE_EMAIL, exception.getMessage())
        );
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 테스트")
    void loginByInvalidEmail() {
        LoginDto loginDto = new LoginDto("없는 이메일!", "1234");
        given(memberRepository.findByEmail(loginDto.getEmail())).willThrow(new BadRequestException("아이디 혹은 비밀번호를 확인하세요."));

        assertThrows(BadRequestException.class, () -> memberService.login(loginDto));
    }

    @Test
    @DisplayName("다른 비밀번호로 로그인 테스트")
    void loginByInvalidPassword() {
        LoginDto loginDto = new LoginDto("sol@sol.com", "sol1234!");
        Member sol = Member.newMember("sol@sol.com", "1234sol!", "sol");
        given(memberRepository.findByEmail(loginDto.getEmail())).willReturn(Optional.of(sol));

        assertThrows(BadRequestException.class, () -> memberService.login(loginDto));
    }
}
