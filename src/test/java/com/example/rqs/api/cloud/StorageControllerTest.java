package com.example.rqs.api.cloud;

import com.example.rqs.api.config.member.TestMember;
import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.core.common.cloud.StorageService;
import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({StorageController.class, JwtProvider.class, RedisDao.class})
@DisplayName("스토리 컨트롤러 테스트")
public class StorageControllerTest {

    @MockBean
    private StorageService storageService;

    @MockBean
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @TestMember
    @DisplayName("이미지 업로드 시, 파일이 없는 경우 400")
    void updateAvatarFailByNullFile() throws Exception {
        UploadDto uploadDto = new UploadDto(null);

        String req = objectMapper.writeValueAsString(uploadDto);

        ResultActions perform = mockMvc.perform(
                post("/api/v1/storage/image/avatar")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(req)
                        .with(csrf())
        );

        perform
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("요청 데이터를 확인하세요.")
                );
    }
}
