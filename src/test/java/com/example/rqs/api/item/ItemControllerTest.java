package com.example.rqs.api.item;

import com.example.rqs.api.RedisDao;
import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.core.item.service.ItemService;
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

@WebMvcTest({ItemController.class, CreateItemValidator.class, UpdateItemValidator.class, JwtProvider.class, RedisDao.class})
@DisplayName("아이템 컨트롤러 테스트")
public class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @MockBean
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("아이템 생성, Question필드 비어있는 경우 예외 처리 400")
    void createItemFailByNullQuestionField() throws Exception {
        CreateItemDto createItemDto = new CreateItemDto(1L, "", "답변이란당", "없엉, 틀려랑, 땡!");
        String req = objectMapper.writeValueAsString(createItemDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }

    @Test
    @WithMockUser
    @DisplayName("아이템 생성, Answer필드 비어있는 경우 예외 처리 400")
    void createItemFailByNullAnswerField() throws Exception {
        CreateItemDto createItemDto = new CreateItemDto(
                1L, "프로세스 어쩌구 저쩌구", "", "없엉, 틀려랑, 땡!");
        String req = objectMapper.writeValueAsString(createItemDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }

    @Test
    @WithMockUser
    @DisplayName("아이템 업데이트, Question필드 비어있는 경우 예외 처리 400")
    void updateItemFailByNullQuestionField() throws Exception {
        CreateItemDto createItemDto = new CreateItemDto(1L, "", "답변이란당", "없엉, 틀려랑, 땡!");
        String req = objectMapper.writeValueAsString(createItemDto);

        ResultActions perform = mockMvc.perform(
                put("/api/v1/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }

    @Test
    @WithMockUser
    @DisplayName("아이템 업데이트, Answer필드 비어있는 경우 예외 처리 400")
    void updateItemFailByNullAnswerField() throws Exception {
        CreateItemDto createItemDto = new CreateItemDto(
                1L, "프로세스 어쩌구 저쩌구", "", "없엉, 틀려랑, 땡!");
        String req = objectMapper.writeValueAsString(createItemDto);

        ResultActions perform = mockMvc.perform(
                put("/api/v1/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req)
                        .with(csrf()));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 데이터를 확인하세요."));
    }
}
