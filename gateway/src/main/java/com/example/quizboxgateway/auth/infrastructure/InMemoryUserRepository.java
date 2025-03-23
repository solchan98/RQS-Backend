package com.example.quizboxgateway.auth.infrastructure;

import com.example.quizboxgateway.auth.domain.OauthType;
import com.example.quizboxgateway.auth.domain.User;
import com.example.quizboxgateway.auth.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InMemoryUserRepository implements UserRepository {

    @Override
    public User save(User user) {
        user.setId(Storage.nextId());
        Storage.initUser(user);

        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        User User = Storage.users.get(id);
        if (User == null) {
            return Optional.empty();
        }

        return Optional.of(User);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Long userId = Storage.userIdByEmail.get(email);
        if (userId == null) {
            return Optional.empty();
        }

        return findById(userId);
    }

    @Override
    public Optional<User> findByOauthIdAndOauthType(String oauthId, OauthType oauthType) {
        return Storage.usersByOauthType.getOrDefault(oauthType, List.of())
                .stream()
                .filter(User -> User.getOauthId().equals(oauthId))
                .findFirst();
    }

    @Override
    public boolean existById(long id) {
        return Storage.users.containsKey(id);
    }

    @Override
    public boolean existByEmail(String email) {
        return Storage.userIdByEmail.containsKey(email);
    }
}
