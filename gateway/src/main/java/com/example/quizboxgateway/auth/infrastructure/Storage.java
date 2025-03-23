package com.example.quizboxgateway.auth.infrastructure;

import com.example.quizboxgateway.auth.domain.OauthType;
import com.example.quizboxgateway.auth.domain.Role;
import com.example.quizboxgateway.auth.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Storage {

    private static long id = 0;

    public static final Map<Long, User> users = new HashMap<>();

    public static final Map<String, Long> userIdByEmail = new HashMap<>();

    public static final Map<String, String> refreshTokens = new HashMap<>();

    public static final Map<String, LocalDateTime> refreshTokenTtl = new HashMap<>();

    public static final Map<OauthType, List<User>> usersByOauthType = new HashMap<>();

    static {
        initUser(
                new User(id++, "sol1", "sol", "a1******",
                        List.of(String.valueOf(Role.USER)), OauthType.LOCAL, null));
        initUser(new User(id++, "sol2@sol.com", "chan", "a1234567******",
                List.of(String.valueOf(Role.USER)), OauthType.LOCAL, null));
    }

    static void initUser(User User) {
        users.put(User.getId(), User);
        userIdByEmail.put(User.getEmail(), User.getId());

        List<User> userEntities = usersByOauthType.getOrDefault(User.getOauthType(), new ArrayList<>());
        userEntities.add(User);
        usersByOauthType.put(User.getOauthType(), userEntities);
    }

    static long nextId() {
        return id++;
    }

    private Storage() {
    }

    public static void clearAll() {
        users.clear();
    }
}
