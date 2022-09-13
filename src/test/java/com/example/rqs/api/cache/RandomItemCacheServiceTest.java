package com.example.rqs.api.cache;

import com.example.rqs.api.cache.randomItem.RandomItemCache;
import com.example.rqs.api.cache.randomItem.RandomItemCacheService;
import com.example.rqs.core.common.redis.RedisConfig;
import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {RedisConfig.class, RedisDao.class, ObjectMapper.class, RandomItemCacheService.class})
@DisplayName("RandomRandomItemCacheService 테스트")
public class RandomItemCacheServiceTest {

    @Autowired
    private RandomItemCacheService randomItemCacheService;

    @Test
    @DisplayName("스페이스 아이템 캐시가 없는 경우")
    void getRandomItemCacheWhenIsEmpty() throws JsonProcessingException {
        Optional<RandomItemCache> optionalRandomItemCache = randomItemCacheService.getCache("1_1");

        assertAll(
                () -> assertThat(optionalRandomItemCache).isEmpty()
        );
    }

    @Test
    @DisplayName("스페이스 아이템 캐시가 있는 경우")
    void getRandomItemCacheWhenIsExist() throws JsonProcessingException {
        Long spaceId = 1L; long memberId = 2L; Long spaceItemSize = 5L;
        String key = spaceId + "_" + memberId;

        randomItemCacheService.addNewCache(key, spaceItemSize, 3);
        Optional<RandomItemCache> optionalRandomItemCache = randomItemCacheService.getCache(key);

        assertThat(optionalRandomItemCache.isEmpty()).isFalse();
        assertAll(
                () -> assertThat(optionalRandomItemCache.get().getSelectableIndexList().size()).isEqualTo(4L)
        );
    }

    @Test
    @DisplayName("(존재할 때,) 스페이스 아이템 존재유무 조회 테스트")
    void existRandomItemCacheByKeyPatternWhenNotExist() throws JsonProcessingException {
        Long spaceId = 1L;
        String key = "1_1";
        Long spaceItemSize = 4L;
        randomItemCacheService.addNewCache(key, spaceItemSize, 3);

        boolean exist = randomItemCacheService.existCacheByKeyPattern(spaceId + "_*");

        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("(존재하지 않을 ,) 스페이스 아이템 존재유무 조회 테스트")
    void existRandomItemCacheByKeyPatternWhenExist() throws JsonProcessingException {
        Long spaceId = 2L;
        String key = "1_1";
        Long spaceItemSize = 4L;
        randomItemCacheService.addNewCache(key, spaceItemSize, 3);

        boolean exist = randomItemCacheService.existCacheByKeyPattern(spaceId + "_*");

        assertThat(exist).isFalse();
    }
}
