package org.example.quizbox.auth.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

//@Configuration
public class EmbeddedRedisConfig {

    private RedisServer redisServer;

    @PostConstruct
    public void init() throws IOException {
        System.out.println("Init Redis!");
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @PreDestroy
    public void destroy() throws IOException {
        System.out.println("Stop Redis!");
        redisServer.stop();
    }
}
