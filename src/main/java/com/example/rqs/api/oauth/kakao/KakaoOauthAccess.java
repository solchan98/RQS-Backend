package com.example.rqs.api.oauth.kakao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoOauthAccess {
    private String access_token;
    private String refresh_token;
    private Long expires_in;
    private String scope;
    private Long refresh_token_expires_in;
}
