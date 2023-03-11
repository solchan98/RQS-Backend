package com.example.rqs.api.oauth.google;

import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.api.oauth.OauthManager;
import com.example.rqs.api.oauth.OauthProfile;
import com.example.rqs.core.common.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service("googleOauth")
@RequiredArgsConstructor
public class GoogleOauthManager implements OauthManager {
    @Value("${oauth.google.client-id}")
    private String CLIENT_ID;
    @Value("${oauth.google.client-secret}")
    private String CLIENT_SECRET;
    @Value("${oauth.google.redirect-uri}")
    private String REDIRECT_URI;
    @Value("${oauth.google.grant-type}")
    private String GRANT_TYPE;

    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public OauthProfile verify(String token) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", token);
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("grant_type", GRANT_TYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        String url = "https://oauth2.googleapis.com/token";

        try {
            GoogleOauthAccess googleOauthAccess = restTemplate.postForObject(url, request, GoogleOauthAccess.class);
            assert googleOauthAccess != null;
            return generateOauthProfile(googleOauthAccess);
        } catch (HttpClientErrorException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private OauthProfile generateOauthProfile(GoogleOauthAccess googleOauthAccess) {
        byte[] decode = jwtProvider.decode(googleOauthAccess.getId_token());
        try {
            GoogleTokenPayload googleTokenPayload = objectMapper.readValue(decode, GoogleTokenPayload.class);
            System.out.println(googleTokenPayload.toString());
            return OauthProfile.of(googleTokenPayload.getSub(), googleTokenPayload.getName(), googleTokenPayload.getPicture());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
