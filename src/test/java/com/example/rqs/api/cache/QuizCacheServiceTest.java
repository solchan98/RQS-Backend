package com.example.rqs.api.cache;

import com.example.rqs.api.cache.quiz.QuizCache;
import com.example.rqs.api.cache.quiz.QuizCacheServiceImpl;
import com.example.rqs.core.common.redis.RedisConfig;
import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {RedisConfig.class, RedisDao.class, ObjectMapper.class, QuizCacheServiceImpl.class})
@DisplayName("QuizCacheService Test")
public class QuizCacheServiceTest {

    @Autowired
    private QuizCacheServiceImpl quizCacheService;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clear() {
        Set<String> keys = redisDao.getKeys("*");
        keys.forEach(redisDao::deleteValues);
    }

    @Test
    @DisplayName("pickRandomQuizId - start 이전(no cache)에 호출시 -1 응답 하는지 테스트")
    void calledPickRandomQuizIdWhenNoCache() {
        // given
        Long spaceId = 1L;
        Long memberId = 1L;

        // when
        Long randomQuizId = quizCacheService.pickRandomQuizId(spaceId, memberId);

        // then
        assertThat(randomQuizId).isEqualTo(-1L);
    }

    @Test
    @DisplayName("start - 기존 데이터 없이 진짜 첫 시작 테스트")
    void start() {
        // given
        Long spaceId = 1L;
        Long memberId = 1L;
        List<Long> itemIds = List.of(1L, 2L, 3L, 4L, 5L);

        // when
        QuizCache quizCache = quizCacheService.start(spaceId, memberId, itemIds);

        // then
        assertAll(
                () -> assertThat(quizCache.getTotal()).isEqualTo(itemIds.size()),
                () -> assertThat(quizCache.quizSize()).isEqualTo(itemIds.size())
        );
    }

    @Test
    @DisplayName("start - 기존 데이터가 있들 때, 시작 테스트")
    void restart() {
        // given
        Long spaceId = 1L;
        Long memberId = 1L;
        List<Long> itemIds = List.of(1L, 2L, 3L);

        List<Long> newItemIds = List.of(1L, 2L, 3L, 4L, 5L);

        // when
        quizCacheService.start(spaceId, memberId, itemIds);
        QuizCache quizCache = quizCacheService.start(spaceId, memberId, newItemIds);

        // then
        assertAll(
                () -> assertThat(quizCache.getTotal()).isEqualTo(newItemIds.size()),
                () -> assertThat(quizCache.quizSize()).isEqualTo(newItemIds.size())
        );
    }

    @Test
    @DisplayName("deleteQuizId - 다른 캐시에서 해당 QuizId가 존재할 때 정상 삭제 테스트")
    void deleteQuizIdWhenDoesExist() throws JsonProcessingException {
        // given
        Long spaceId = 1L;
        Long memberId = 1L;
        List<Long> itemIds = List.of(1L, 2L, 3L, 4L, 5L);
        quizCacheService.start(spaceId, memberId, itemIds);

        // when
        quizCacheService.deleteQuiz(spaceId, 3L);
        String values = redisDao.getValues("1_1");
        QuizCache quizCache = objectMapper.readValue(values, QuizCache.class);

        // then
        assertThat(quizCache.quizSize()).isEqualTo(4);

    }
}
