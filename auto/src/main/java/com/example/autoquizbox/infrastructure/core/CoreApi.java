package com.example.autoquizbox.infrastructure.core;

import com.example.autoquizbox.entities.AutoQuizPack;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CoreApi {

    private final RestClient restClient;

    private static final String AUTHORIZATION_HEADER = "X-USER-ID";

    private final ObjectMapper objectMapper;

    private CoreApi(@Qualifier("coreServerClient") RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public CoreApiResponse<Long> commandCreateSimpleQuizPack(
            long userId,
            AutoQuizPack autoQuizPack
    ) {
        return restClient.post()
                .uri("/quiz-packs/simple")
                .header("X-USER-ID", String.valueOf(userId))
                .body(autoQuizPack)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<Long>>() {})
                .getBody();
    }

    protected void handleError(ClientHttpResponse res) throws IOException {
        HttpStatusCode statusCode = res.getStatusCode();
        CoreApiErrorResponse coreApiErrorResponse = toCoreApiErrorResponse(res);

        throw new ApiException(
                HttpStatus.valueOf(statusCode.value()),
                coreApiErrorResponse.getMessage(),
                coreApiErrorResponse.getData()
        );
    }

    private CoreApiErrorResponse toCoreApiErrorResponse(ClientHttpResponse res) {
        try {
            String responseBody = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
            return objectMapper.readValue(responseBody, CoreApiErrorResponse.class);
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }
}
