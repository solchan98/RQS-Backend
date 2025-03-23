package com.example.quizboxgateway.auth.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthService {

    private final UserRepository userRepository;

    public AuthUserDetails signIn(OauthType oauthType, String oauthId) {
        return userRepository.findByOauthIdAndOauthType(oauthId, oauthType)
                .orElseGet(() -> setUpInitUser(oauthType, oauthId))
                .toAuthUserDetails();
    }

    private User setUpInitUser(OauthType oauthType, String oauthId) {
        return userRepository.save(User.newOauthMember(oauthType, oauthId));
    }
}
