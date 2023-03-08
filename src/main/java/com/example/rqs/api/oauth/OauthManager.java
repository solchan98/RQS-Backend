package com.example.rqs.api.oauth;

public interface OauthManager {
    OauthProfile verify(String token);
}
