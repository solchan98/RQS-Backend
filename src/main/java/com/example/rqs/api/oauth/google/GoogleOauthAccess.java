package com.example.rqs.api.oauth.google;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleOauthAccess {
    private String access_token;
    private Long expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
