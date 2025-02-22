package com.example.quizboxgateway.apis.autoquiz.core;

import com.example.quizboxgateway.apis.autoquiz.AutoQuizApi;
import com.example.quizboxgateway.apis.autoquiz.AutoQuizApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AutoQuizCoreApi extends AutoQuizApi {

    private final RestClient autoQuizClient;

    protected AutoQuizCoreApi(@Qualifier("autoQuizClient") RestClient autoQuizClient, ObjectMapper objectMapper) {
        super(objectMapper);
        this.autoQuizClient = autoQuizClient;
    }

    public AutoQuizApiResponse<QueryAutoQuizPackResponse> queryAutoQuizPack(long userId, long taskId) {
        return autoQuizClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/auto/task/")
                        .path(String.valueOf(taskId))
                        .path("/quiz-pack")
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<QueryAutoQuizPackResponse>>() {
                })
                .getBody();
    }

    public AutoQuizApiResponse<QueryAutoQuizPackResponse> commandUpdateAutoQuizPack(
            long userId,
            CommandAutoQuizPackUpdateRequest request
    ) {
        return autoQuizClient.put()
                .uri("/auto/auto-quiz-pack")
                .body(request)
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<QueryAutoQuizPackResponse>>() {
                })
                .getBody();
    }

    public AutoQuizApiResponse<Long> commandAutoQuizPack(long userId, long autoQuizPackId) {
        return autoQuizClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/auto/auto-quiz-pack/")
                        .path(String.valueOf(autoQuizPackId))
                        .path("/quiz-pack")
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<Long>>() {
                })
                .getBody();
    }
}
