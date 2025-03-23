package com.example.quizboxgateway.auth.infrastructure.oauth.kakao;

import com.example.quizboxgateway.common.ApiException;
import com.example.quizboxgateway.common.ServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class KakaoOauthApi {

    private final ObjectMapper objectMapper;
    private final RestClient kakaoOauthClient;

    public KakaoOauthApi(@Qualifier("kakaoOauthClient") RestClient kakaoOauthClient, ObjectMapper objectMapper) {
        this.kakaoOauthClient = kakaoOauthClient;
        this.objectMapper = objectMapper;
    }

    public KakaoReadMeResponse getAccountByAccessToken(String accessToken) {
        return kakaoOauthClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/user/me")
                        .build()
                )
                .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<KakaoReadMeResponse>() {
                })
                .getBody();
    }

    protected void handleError(ClientHttpResponse res) throws IOException {
        HttpStatusCode statusCode = res.getStatusCode();
        KakaoReadMeErrorResponse kakaoReadMeErrorResponse = toKakaoReadMeErrorResponse(res);

        throw new ApiException(
                HttpStatus.valueOf(statusCode.value()),
                kakaoReadMeErrorResponse.getMsg(),
                null
        );
    }

    private KakaoReadMeErrorResponse toKakaoReadMeErrorResponse(ClientHttpResponse res) {
        try {
            String responseBody = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
            return objectMapper.readValue(responseBody, KakaoReadMeErrorResponse.class);
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }
}
