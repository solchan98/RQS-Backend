package com.example.rqs.api.cache.quiz;

import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizCacheServiceImpl implements QuizCacheService {
    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;

    /** will be return -1 if it doesn't exist quiz data */
    @Override
    public Long pickRandomQuizId(Long spaceId, Long memberId) {
        String key = getKey(spaceId, memberId);
        String gameInfo = redisDao.getValues(key);
        if (gameInfo.isEmpty()) {
            return -1L;
        }

        QuizCache quizCache = convertToQuizCache(gameInfo);
        Long randomQuizId = quizCache.pickQuizId();
        cache(key, quizCache);
        return randomQuizId;
    }

    @Override
    public QuizCache start(Long spaceId, Long memberId, List<Long> itemIds) {
        QuizCache quizCache = QuizCache.of(itemIds);
        cache(getKey(spaceId, memberId), quizCache);
        return quizCache;
    }

    private void cache(String key, QuizCache quizCache) {
        if (quizCache.quizSize() == 0) {
            redisDao.deleteValues(key);
            return;
        }
        redisDao.setValues(key, convertToString(quizCache));
    }

    private QuizCache convertToQuizCache(String values) {
        try {
            return objectMapper.readValue(values, QuizCache.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertToString(QuizCache quizCache) {
        try {
            return objectMapper.writeValueAsString(quizCache);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
