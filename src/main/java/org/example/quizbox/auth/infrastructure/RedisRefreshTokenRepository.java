package org.example.quizbox.auth.infrastructure;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.auth.refresh.RefreshTokenRepository;
import org.springframework.stereotype.Repository;

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
