package com.example.rqs.api.oauth.kakao;

import com.example.rqs.api.oauth.OauthManager;
import com.example.rqs.api.oauth.OauthProfile;
import com.example.rqs.core.common.exception.BadRequestException;
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

@Service("kakaoOauth")
@RequiredArgsConstructor
public class KakaoOauthManager implements OauthManager {
    @Value("${oauth.kakao.client-id}")
    private String CLIENT_ID;
    @Value("${oauth.kakao.redirect-uri}")
    private String REDIRECT_URI;
    @Value("${oauth.kakao.grant-type}")
    private String GRANT_TYPE;

    private final RestTemplate restTemplate;

    @Override
    public OauthProfile verify(String token) {
        KakaoOauthAccess oauthAccess = getOauthAccess(token);
        KakaoProfile profile = getProfile(oauthAccess);
        return generateOauthProfile(profile);
    }

    private KakaoOauthAccess getOauthAccess(String token) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", token);
        body.add("client_id", CLIENT_ID);
        body.add("grant_type", GRANT_TYPE);
        body.add("redirect_uri", REDIRECT_URI);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        String url = "https://kauth.kakao.com/oauth/token";

        try {
            return restTemplate.postForObject(url, request, KakaoOauthAccess.class);
        } catch (HttpClientErrorException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private KakaoProfile getProfile(KakaoOauthAccess access) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(access.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        String url = "https://kapi.kakao.com/v2/user/me";

        try {
            return restTemplate.postForObject(url, request, KakaoProfile.class);
        } catch (HttpClientErrorException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private OauthProfile generateOauthProfile(KakaoProfile profile) {
        return OauthProfile.of(
                String.valueOf(profile.getId()),
                profile.getProperties().getNickname(),
                profile.getProperties().getProfile_image()
        );
    }
}
