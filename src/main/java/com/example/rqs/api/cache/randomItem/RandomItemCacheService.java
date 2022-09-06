package com.example.rqs.api.cache.randomItem;

import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Component
public class RandomItemCacheService {

    private final ObjectMapper objectMapper;

    private final RedisDao redisDao;

    public RandomItemCacheService(ObjectMapper objectMapper, RedisDao redisDao) {
        this.objectMapper = objectMapper;
        this.redisDao = redisDao;
    }

    public void addCache(Long spaceId, Long spaceMemberId, RandomItemCache randomItemCache) throws JsonProcessingException {
        String objectAsString = objectMapper.writeValueAsString(randomItemCache);
        String key = spaceId + "_" +spaceMemberId;
        redisDao.setValues(key, objectAsString, Duration.ofMinutes(5));
    }

    public Optional<RandomItemCache> getCache(Long spaceId, Long spaceMemberId) throws JsonProcessingException {
        String key = spaceId + "_" +spaceMemberId;
        String stringObject = redisDao.getValues(key);
        return Objects.isNull(stringObject)
                ? Optional.empty()
                : Optional.of(objectMapper.readValue(stringObject, RandomItemCache.class));
    }
}
