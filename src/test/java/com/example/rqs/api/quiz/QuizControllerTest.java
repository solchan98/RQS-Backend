package com.example.rqs.api.quiz;

import com.example.rqs.api.cache.quiz.QuizCacheService;
import com.example.rqs.api.common.CommonAPIAuthChecker;
import com.example.rqs.core.common.redis.RedisDao;
import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.core.quiz.service.*;
import com.example.rqs.core.member.service.MemberAuthService;
import com.example.rqs.core.quiz.service.dtos.CreateAnswer;
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

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({QuizController.class, CreateQuizValidator.class, UpdateQuizValidator.class, JwtProvider.class, RedisDao.class, QuizCacheService.class, CommonAPIAuthChecker.class})
@DisplayName("아이템 컨트롤러 테스트")
public class QuizControllerTest {

    @MockBean
    private QuizReadService quizReadService;
    @MockBean
    private QuizAuthService quizAuthService;
    @MockBean
    private QuizCacheService itemRandomService;
    @MockBean
    private QuizUpdateService quizUpdateService;
    @MockBean
    private QuizRegisterService quizRegisterService;

    @MockBean
    private MemberAuthService memberAuthService;

    @MockBean
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("퀴즈 생성, Question필드 비어있는 경우 예외 처리 400")
    void createQuizFailByNullQuestionField() throws Exception {
        CreateQuizDto createQuizDto = new CreateQuizDto(1L, "",  List.of(CreateAnswer.of("answer", true)), "없엉, 틀려랑, 땡!");
        String req = objectMapper.writeValueAsString(createQuizDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/my/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }

    @Test
    @WithMockUser
    @DisplayName("퀴즈 생성, Answer필드 비어있는 경우 예외 처리 400")
    void createQuizFailByNullAnswerField() throws Exception {
        CreateQuizDto createQuizDto = new CreateQuizDto(
                1L, "프로세스 어쩌구 저쩌구", List.of(CreateAnswer.of("answer", true)), "없엉, 틀려랑, 땡!");
        String req = objectMapper.writeValueAsString(createQuizDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/my/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }

    @Test
    @WithMockUser
    @DisplayName("퀴즈 업데이트, Question필드 비어있는 경우 예외 처리 400")
    void updateQuizFailByNullQuestionField() throws Exception {
        CreateQuizDto createQuizDto = new CreateQuizDto(1L, "", List.of(CreateAnswer.of("answer", true)), "없엉, 틀려랑, 땡!");
        String req = objectMapper.writeValueAsString(createQuizDto);

        ResultActions perform = mockMvc.perform(
                put("/api/v1/my/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }

    @Test
    @WithMockUser
    @DisplayName("퀴즈 업데이트, Answer필드 비어있는 경우 예외 처리 400")
    void updateQuizFailByNullAnswerField() throws Exception {
        CreateQuizDto createQuizDto = new CreateQuizDto(
                1L, "프로세스 어쩌구 저쩌구", List.of(CreateAnswer.of("answer", true)), "없엉, 틀려랑, 땡!");
        String req = objectMapper.writeValueAsString(createQuizDto);

        ResultActions perform = mockMvc.perform(
                put("/api/v1/my/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }
}
