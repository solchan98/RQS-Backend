package com.example.autoquizbox.infrastructure.core;

import com.example.autoquizbox.application.PreviewResponse;
import com.example.autoquizbox.domain.vo.AutoQuizPack;
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
import java.util.Map;

@Component
public class CoreApi {

    private final RestClient restClient;

    private static final String AUTHORIZATION_HEADER = "X-USER-ID";

    private final ObjectMapper objectMapper;

    private CoreApi(@Qualifier("coreServerClient") RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public CoreApiResponse<PreviewResponse> commandPreviewQuizPack(long userId, long taskId, AutoQuizPack autoQuizPack) {
        Map<String, Object> requestBody = Map.of(
                "taskId", taskId,
                "userId", userId,
                "title", autoQuizPack.getTitle(),
                "description", autoQuizPack.getDescription(),
                "keywords", autoQuizPack.getKeywords(),
                "quizzes", autoQuizPack.getQuizzes()
        );

        return restClient.post()
                .uri("preview")
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<PreviewResponse>>() {
                })
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
