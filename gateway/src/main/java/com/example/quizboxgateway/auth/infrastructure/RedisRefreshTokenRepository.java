package com.example.quizboxgateway.auth.infrastructure;

import com.example.quizboxgateway.auth.refresh.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

    private final RedisClient redisClient;

    @Override
    public void save(String email, String token, long ttl) {
        redisClient.setValues(email, token, Duration.ofMillis(ttl));
    }

    @Override
    public Optional<String> findByEmail(String email) {
        return Optional.ofNullable(redisClient.getValues(email));
    }
}
