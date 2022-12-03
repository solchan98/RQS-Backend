package com.example.rqs.api.space;

import com.example.rqs.api.common.CommonAPIAuthChecker;
import com.example.rqs.core.common.redis.RedisDao;
import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.core.member.service.MemberService;
import com.example.rqs.core.space.service.SpaceService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SpaceController.class, JoinSpaceValidator.class, JwtProvider.class, CommonAPIAuthChecker.class, RedisDao.class})
@DisplayName("스페이스 컨트롤러 테스트")
public class SpaceControllerTest {

    @MockBean
    private SpaceService spaceService;

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
    @DisplayName("스페이스 생성 시, 타이틀이 비어있는 경우 예외 400")
    void createSpaceFailByEmptyTitle() throws Exception {
        CreateSpaceDto createSpaceDto = new CreateSpaceDto("", false);
        String req = objectMapper.writeValueAsString(createSpaceDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/my/space")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }

    @Test
    @WithMockUser
    @DisplayName("스페이스 타이틀 업데이트 시, 타이틀이 비어있는 경우 예외 400")
    void updateSpaceFailByEmptyTitle() throws Exception {
        UpdateSpaceDto updateSpaceDto = new UpdateSpaceDto(1L, "");
        String req = objectMapper.writeValueAsString(updateSpaceDto);

        ResultActions perform = mockMvc.perform(
                patch("/api/v1/my/space")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }
}
