package com.example.rqs.api.cache;

import com.example.rqs.api.cache.randomItem.RandomItemCache;
import com.example.rqs.api.cache.randomItem.RandomItemCacheService;
import com.example.rqs.core.common.redis.RedisConfig;
import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {RedisConfig.class, RedisDao.class, ObjectMapper.class, RandomItemCacheService.class})
@DisplayName("RandomRandomItemCacheService 테스트")
public class RandomItemCacheServiceTest {

    @Autowired
    private RandomItemCacheService randomItemCacheService;

    @Autowired
    private RedisDao redisDao;

    @AfterEach
    void clearCache() {
        Set<String> keySet = redisDao.getKeys("*");
        keySet.forEach(redisDao::deleteValues);
    }

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
    @DisplayName("(존재하지 않을 때,) 스페이스 아이템 존재유무 조회 테스트")
    void existRandomItemCacheByKeyPatternWhenExist() throws JsonProcessingException {
        Long spaceId = 2L;
        String key = "1_1";
        Long spaceItemSize = 4L;
        randomItemCacheService.addNewCache(key, spaceItemSize, 3);

        boolean exist = randomItemCacheService.existCacheByKeyPattern(spaceId + "_*");

        assertThat(exist).isFalse();
    }

    @Test
    @DisplayName("한 스페이스 캐싱 데이터에서 특정 selectable 인덱스 제거 테스트 (성공)")
    void successDeleteIndexInCache() throws JsonProcessingException {
        List<Long> itemIdList = List.of(12L, 26L, 74L, 96L, 101L, 104L, 109L);
        Long spaceId = 1L;
        long member1 = 1L; long member2 = 2L;
        randomItemCacheService.addNewCache(spaceId + "_" + member1, 7L, 4);
        randomItemCacheService.addNewCache(spaceId + "_" + member2, 7L, 2);

        int deleteIdIndexInItemList = 5; // delete 104L (calculated by itemService.getItemIndex())
        randomItemCacheService.deleteIndexInCache(spaceId, deleteIdIndexInItemList);
        Optional<RandomItemCache> member1Cache = randomItemCacheService.getCache(spaceId + "_" + member1);
        Optional<RandomItemCache> member2Cache = randomItemCacheService.getCache(spaceId + "_" + member2);

        assertAll(
                () -> assertThat(member1Cache).isPresent(),
                () -> assertThat(member2Cache).isPresent()
        );

        assertAll(
                () -> assertThat(member1Cache.get().getSelectableIndexList()).isEqualTo(List.of(0L, 1L, 2L, 3L, 5L)),
                () -> assertThat(member2Cache.get().getSelectableIndexList()).isEqualTo(List.of(0L, 1L, 3L, 4L, 5L))
        );


    }
}
