package com.example.quizboxgateway.auth.infrastructure;

import com.example.quizboxgateway.auth.auth.Role;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Storage {

    public static final Map<Long, UserEntity> users = new HashMap<>();

    public static final Map<String, Long> userIdByEmail = new HashMap<>();

    public static final Map<String, String> refreshTokens = new HashMap<>();

    public static final Map<String, LocalDateTime> refreshTokenTtl = new HashMap<>();

    static {
        initUser(new UserEntity(1L, "sol1", "sol", "a1******", List.of(String.valueOf(Role.USER))));
        initUser(new UserEntity(2L, "sol2@sol.com", "chan", "a1234567******", List.of(String.valueOf(Role.USER))));
    }

    static void initUser(UserEntity userEntity) {
        users.put(userEntity.getId(), userEntity);
        userIdByEmail.put(userEntity.getEmail(), userEntity.getId());
    }

    private Storage() {
    }

    public static void clearAll() {
        users.clear();
    }
}
