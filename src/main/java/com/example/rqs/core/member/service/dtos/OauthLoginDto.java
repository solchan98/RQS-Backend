package com.example.rqs.core.member.service.dtos;

import lombok.Getter;

@Getter
public class OauthLoginDto {
    private final String id;
    private final String nickname;
    private final String avatar;
    private final String type;

    private OauthLoginDto(String id, String nickname, String avatar, String type) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.type = type;
    }

    public static OauthLoginDto of(String id, String nickname, String avatar, String type) {
        return new OauthLoginDto(id, nickname, avatar, type);
    }
}
