package com.example.rqs.core.member;


import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.MemberServiceImpl;
import com.example.rqs.core.member.service.dtos.SignUpDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤서 서비스 테스트")
public class MemberServiceTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberRepository memberRepository;

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
}
