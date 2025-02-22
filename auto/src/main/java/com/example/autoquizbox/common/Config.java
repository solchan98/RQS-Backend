package com.example.autoquizbox.common;

import com.example.autoquizbox.infrastructure.PausableThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Configuration
public class Config {

    @Bean
    public PausableThreadPoolExecutor getPausableThreadPoolExecutor() {
        return new PausableThreadPoolExecutor(
                2,
                2,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
    }
}
