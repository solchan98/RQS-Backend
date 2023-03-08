package com.example.rqs.api.oauth.google;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleTokenPayload {
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    private Boolean email_verified;
    private String at_hash;
    private String name;
    private String picture;
    private String locale;
    private Long iat;
    private Long exp;
}
