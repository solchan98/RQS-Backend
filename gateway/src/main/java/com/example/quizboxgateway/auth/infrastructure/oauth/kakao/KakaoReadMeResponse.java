package com.example.quizboxgateway.auth.infrastructure.oauth.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class KakaoReadMeResponse {

    private String id;
    private LocalDateTime connectedAt;
    private Properties properties;

    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class Properties {
        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }

    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class kakaoAccount {
        private boolean profileNicknameNeedsAgreement;
        private boolean profileImageNeedsAgreement;
        private Profile profile;
    }

    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class Profile {
        private String nickname;
        private String thumbnailImageUrl;
        private String profileImageUrl;
        private boolean isDefaultImage;
        private boolean isDefaultNickName;
    }
}


/**
 * {
 * "id": 2702128167,
 * "connected_at": "2023-03-11T12:53:37Z",
 * "properties": {
 * "nickname": "박솔찬",
 * "profile_image": "http://k.kakaocdn.net/dn/ixAB2/btsMvekO4FG/nxvbotUYfRFmTW8h23SNt1/img_640x640.jpg",
 * "thumbnail_image": "http://k.kakaocdn.net/dn/ixAB2/btsMvekO4FG/nxvbotUYfRFmTW8h23SNt1/img_110x110.jpg"
 * },
 * "kakao_account": {
 * "profile_nickname_needs_agreement": false,
 * "profile_image_needs_agreement": false,
 * "profile": {
 * "nickname": "박솔찬",
 * "thumbnail_image_url": "http://k.kakaocdn.net/dn/ixAB2/btsMvekO4FG/nxvbotUYfRFmTW8h23SNt1/img_110x110.jpg",
 * "profile_image_url": "http://k.kakaocdn.net/dn/ixAB2/btsMvekO4FG/nxvbotUYfRFmTW8h23SNt1/img_640x640.jpg",
 * "is_default_image": false,
 * "is_default_nickname": false
 * }
 * }
 * }
 */