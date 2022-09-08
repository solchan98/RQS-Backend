package com.example.rqs.api.cache.randomItem;

import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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

    private void addCache(String key, RandomItemCache randomItemCache) throws JsonProcessingException {
        String objectAsString = objectMapper.writeValueAsString(randomItemCache);
        redisDao.setValues(key, objectAsString, Duration.ofMinutes(5));
    }

    public void addNewCache(String key, Long spaceItemSize, int selectedCacheIndex) throws JsonProcessingException {
        List<Long> itemIndexList = new ArrayList<>(spaceItemSize.intValue());
        for (long i = 0L; i < spaceItemSize; i++) itemIndexList.add(i);
        RandomItemCache randomItemCache = new RandomItemCache(itemIndexList);
        randomItemCache.getSelectableIndexList().remove(selectedCacheIndex);
        this.addCache(key, randomItemCache);
    }

    public Optional<RandomItemCache> getCache(String key) throws JsonProcessingException {
        String stringObject = redisDao.getValues(key);
        return Objects.isNull(stringObject)
                ? Optional.empty()
                : Optional.of(objectMapper.readValue(stringObject, RandomItemCache.class));
    }

    public void updateCache(String key, int selectedCacheIndex) throws JsonProcessingException {
        String values = redisDao.getValues(key);
        RandomItemCache itemCache = objectMapper.readValue(values, RandomItemCache.class);
        itemCache.getSelectableIndexList().remove(selectedCacheIndex);
        this.addCache(key, itemCache);
    }
}
