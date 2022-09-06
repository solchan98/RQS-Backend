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

import java.util.List;
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
        Optional<RandomItemCache> optionalRandomItemCache = randomItemCacheService.getCache(1L, 1L);

        assertAll(
                () -> assertThat(optionalRandomItemCache).isEmpty()
        );
    }

    @Test
    @DisplayName("스페이스 아이템 캐시가 있는 경우")
    void getRandomItemCacheWhenIsExist() throws JsonProcessingException {
        Long spaceId = 1L; Long spaceMemberId = 2L;
        RandomItemCache randomItemCache = new RandomItemCache(3L, List.of(1L, 4L, 12L));

        randomItemCacheService.addCache(spaceId, spaceMemberId, randomItemCache);
        Optional<RandomItemCache> optionalRandomItemCache = randomItemCacheService.getCache(spaceId, spaceMemberId);

        assertThat(optionalRandomItemCache.isEmpty()).isFalse();
        assertAll(
                () -> assertThat(optionalRandomItemCache.get().getTotalCnt()).isEqualTo(3L),
                () -> assertThat(optionalRandomItemCache.get().getSelectableIndexList().size()).isEqualTo(3L)
        );
    }
}
