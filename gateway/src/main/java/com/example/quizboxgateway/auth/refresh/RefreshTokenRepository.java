package com.example.quizboxgateway.auth.refresh;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(String email, String token, long ttl);

    Optional<String> findByEmail(String email);
}
