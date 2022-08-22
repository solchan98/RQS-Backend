package com.example.rqs.api.member;

import com.example.rqs.api.RedisDao;
import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.core.member.service.MemberService;
import com.example.rqs.core.member.service.dtos.SignUpDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({MemberController.class, SignUpValidator.class, JwtProvider.class, RedisDao.class})
@DisplayName("멤버 컨트롤러 테스트")
public class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("회원가입 필드가 비어있는 경우 예외 처리 400")
    void signUpFailByNullData() throws Exception {
        SignUpDto signUpDto = new SignUpDto("", "", "터저랏");
        String req = objectMapper.writeValueAsString(signUpDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/member/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }

    @Test
    @WithMockUser
    @DisplayName("이메일 정규식 미충족 400")
    void signUpFailByEmail() throws Exception {
        SignUpDto signUpDto = new SignUpDto("이메일틀렷", "1234", "터저랏");
        String req = objectMapper.writeValueAsString(signUpDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/member/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf())
        );

        perform
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("이메일 형식에 맞지 않습니다.")
                );
    }

    @Test
    @WithMockUser
    @DisplayName("비밀번호 정규식 미충족 400")
    void signUpFailByPassword() throws Exception {
        SignUpDto signUpDto = new SignUpDto("sol@sol.com", "1234땡!", "터저랏");
        String req = objectMapper.writeValueAsString(signUpDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/member/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf())
        );

        perform
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
                );
    }
}
