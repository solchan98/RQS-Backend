package com.example.rqs.core.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDao {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisDao(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public Duration getExpiredAt(String key) throws RuntimeException {
        Long expire = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        boolean isExpired = Objects.isNull(expire) || expire <= 0;
        if (isExpired) throw new RuntimeException("The key has expired");
        return Duration.ofMillis(expire);
    }

    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
