package com.example.quizboxgateway.auth.domain;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByOauthIdAndOauthType(String oauthId, OauthType oauthType);

    boolean existById(long id);

    boolean existByEmail(String email);
}
