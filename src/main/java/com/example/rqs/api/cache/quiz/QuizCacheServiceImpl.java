package com.example.rqs.api.cache.quiz;

import com.example.rqs.api.quiz.randomquiz.InProgressResponse;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class QuizCacheServiceImpl implements QuizCacheService {
    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;

    @Override
    public Long pickRandomQuizId(Long spaceId, Long memberId) {
        String key = getKey(spaceId, memberId);
        String gameInfo = redisDao.getValues(key);
        if (gameInfo == null) {
            throw new BadRequestException("뽑을 수 있는 퀴즈가 존재하지 않습니다.");
        }

        QuizCache quizCache = convertToQuizCache(gameInfo);
        Long randomQuizId = quizCache.pickQuizId();
        cache(key, quizCache);
        return randomQuizId;
    }

    @Override
    public QuizCache start(Long spaceId, Long memberId, List<Long> itemIds, String type) {
        QuizCache quizCache = QuizCache.of(itemIds, type);
        cache(getKey(spaceId, memberId), quizCache);
        return quizCache;
    }

    @Override
    public void deleteQuiz(Long spaceId, Long quizId) {
        Set<String> keys = redisDao.getKeys(spaceId + "*");
        for (String key: keys) {
            String values = redisDao.getValues(key);
            QuizCache quizCache = convertToQuizCache(values);
            quizCache.removeQuizId(quizId);
            cache(key, quizCache);
        }
    }

    @Override
    public InProgressResponse inProgress(Long spaceId, Long memberId) {
        String values = redisDao.getValues(getKey(spaceId, memberId));
        if (values == null) {
            return InProgressResponse.of(false);
        }

        QuizCache quizCache = convertToQuizCache(values);
        return InProgressResponse.from(quizCache);
    }

    private void cache(String key, QuizCache quizCache) {
        if (quizCache.quizSize() == 0) {
            redisDao.deleteValues(key);
            return;
        }
        redisDao.setValues(key, convertToString(quizCache), Duration.ofSeconds(defaultTTL));
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
