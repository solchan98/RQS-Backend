package com.example.rqs.core.redis;

import com.example.rqs.core.common.redis.RedisDao;
import com.example.rqs.core.common.redis.RedisConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {RedisConfig.class, RedisDao.class})
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @AfterEach
    void clearCache() {
        Set<String> keySet = redisDao.getKeys("*");
        keySet.forEach(redisDao::deleteValues);
    }

    @Test
    @DisplayName("Redis 값 저장 및 조회 테스트")
    void redisTest() {
        redisDao.setValues("key", "값!");
        String value = redisDao.getValues("key");
        assertThat(value).isEqualTo("값!");
    }

    @Test
    @DisplayName("Redis Expired 테스트")
    void testRedisExpired() throws InterruptedException {
        redisDao.setValues("key", "값!", Duration.ofMillis(500));
        Thread.sleep(600);
        String value = redisDao.getValues("key");
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("키 패턴을 통한 키 존재 여부 테스트")
    void testGetKeysByPattern() {
        redisDao.setValues("pattern_1", "f", Duration.ofMillis(500));
        redisDao.setValues("pattern_2", "s", Duration.ofMillis(500));
        redisDao.setValues("not_pattern_2", "s", Duration.ofMillis(500));

        Set<String> keySet = redisDao.getKeys("pattern_*");

        assertThat(keySet.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("getExpiredAt - 정상 케이스 테스트")
    void testGetExpiredAt() throws InterruptedException {
        redisDao.setValues("key", "value", Duration.ofSeconds(6));

        Thread.sleep(1500);
        Duration expire = redisDao.getExpiredAt("key");

        assertThat(expire.getSeconds()).isLessThan(5);
    }

    @Test
    @DisplayName("getExpiredAt - 키가 만료된 경우")
    void testGetExpiredAtWhenTheKeysHasExpired() throws InterruptedException {
        redisDao.setValues("key", "value", Duration.ofSeconds(1));
        Thread.sleep(1500);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> redisDao.getExpiredAt("key"));
        assertThat(exception.getMessage()).isEqualTo("The key has expired");
    }
}
