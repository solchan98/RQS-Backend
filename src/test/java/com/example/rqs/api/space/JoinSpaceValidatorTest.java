package com.example.rqs.api.space;

import com.example.rqs.api.jwt.JwtProviderImpl;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.redis.RedisConfig;
import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {RedisConfig.class, JwtProviderImpl.class, RedisDao.class, ObjectMapper.class, JoinSpaceValidator.class})
@DisplayName("JoinSpaceValidator 테스트")
public class JoinSpaceValidatorTest {

    @Autowired
    private JoinSpaceValidator joinSpaceValidator;

    @Test
    @DisplayName("만료된 스페이스 참여 토큰으로 참여 요청 테스트")
    void joinSpaceByExpiredToken() {
        String exitedITK = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJzcGFjZUlkXCI6MSxcInNwYWNlVGl0bGVcIjpcIuuwseyXlOuTnCDrqbTsoJEg7KeI66y4IOumrOyKpO2KuFwiLFwiaW52aXRlcklkXCI6MSxcImludml0ZXJOaWNrbmFtZVwiOlwic29sXCJ9IiwiaWF0IjoxNjYzNDgyMTA0LCJleHAiOjE2NjM0ODI0MDR9.8gfPoN5sL8iMz-VAfmIAN1pstuoPcQdJETfa6EGcjs";

        BadRequestException exception = assertThrows(BadRequestException.class, () -> joinSpaceValidator.validate(exitedITK));

        assertThat(exception.getMessage()).isEqualTo("링크가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("잘못된 스페이스 참여 토큰으로 참여 요청 테스트")
    void joinSpaceByInvalidToken() {
        String exitedITK = "invalid token";

        BadRequestException exception = assertThrows(BadRequestException.class, () -> joinSpaceValidator.validate(exitedITK));

        assertThat(exception.getMessage()).isEqualTo("링크가 올바르지 않습니다.");
    }
}
