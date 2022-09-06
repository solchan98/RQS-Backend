package com.example.rqs.api.redis;

import com.example.rqs.core.common.redis.RedisDao;
import com.example.rqs.core.common.redis.RedisConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest(classes = {RedisConfig.class, RedisDao.class})
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Test
    @DisplayName("Redis 값 저장 및 조회 테스트")
    void redisTest() {
        redisDao.setValues("key", "값!");
        String value = redisDao.getValues("key");
        Assertions.assertThat(value).isEqualTo("값!");
    }

    @Test
    @DisplayName("Redis Expired 테스트")
    void redisExpiredTest() throws InterruptedException {
        redisDao.setValues("key", "값!", Duration.ofMillis(500));
        Thread.sleep(600);
        String value = redisDao.getValues("key");
        Assertions.assertThat(value).isNull();
    }
}
