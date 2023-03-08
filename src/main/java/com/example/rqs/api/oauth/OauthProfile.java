package com.example.rqs.api.oauth;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OauthProfile {
    private final String id;
    private final String nickname;
    private final String avatar;

    private OauthProfile(String id, String nickname, String avatar) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public static OauthProfile of(String id, String nickname, String avatar) {
        return new OauthProfile(id, nickname, avatar);
    }
}
