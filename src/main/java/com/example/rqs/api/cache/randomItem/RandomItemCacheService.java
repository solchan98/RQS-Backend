package com.example.rqs.api.cache.randomItem;

import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
public class RandomItemCacheService {

    private final ObjectMapper objectMapper;

    private final RedisDao redisDao;

    @Value("${spring.item.live}")
    private Long itemCacheTime;

    public RandomItemCacheService(ObjectMapper objectMapper, RedisDao redisDao) {
        this.objectMapper = objectMapper;
        this.redisDao = redisDao;
    }

    private void addCache(String key, RandomItemCache randomItemCache) throws JsonProcessingException {
        String objectAsString = objectMapper.writeValueAsString(randomItemCache);
        redisDao.setValues(key, objectAsString, Duration.ofMillis(itemCacheTime));
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

    public boolean existCacheByKeyPattern(String keyPattern) {
        Set<String> keySet = redisDao.getKeys(keyPattern);
        return keySet.size() > 0;
    }

    public void deleteIndexInCache(Long spaceId, int deleteIdIndexInItemList) throws JsonProcessingException {
        Set<String> keySet = redisDao.getKeys(spaceId + "_*");
        for (String key: keySet) {
            String values = redisDao.getValues(key);
            RandomItemCache randomItemCache = objectMapper.readValue(values, RandomItemCache.class);
            List<Long> selectableIndexList = randomItemCache.getSelectableIndexList();
            int deleteIndex = selectableIndexList.indexOf((long) deleteIdIndexInItemList);
            if (deleteIndex != -1) selectableIndexList.remove(deleteIndex);
            for (int index = 0; index < selectableIndexList.size(); index++) {
                if (selectableIndexList.get(index) > deleteIdIndexInItemList) {
                    selectableIndexList.set(index, selectableIndexList.get(index) - 1);
                }
            }
            addCache(key, randomItemCache);
        }
    }
}
