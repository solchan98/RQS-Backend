package com.example.quizboxgateway.apis.autoquiz;

import com.example.quizboxgateway.common.ApiException;
import com.example.quizboxgateway.common.ServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class AutoQuizApi {

    protected static final String AUTHORIZATION_HEADER = "X-USER-ID";

    private final ObjectMapper objectMapper;

    protected AutoQuizApi(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected void handleError(ClientHttpResponse res) throws IOException {
        HttpStatusCode statusCode = res.getStatusCode();
        AutoQuizApiErrorResponse autoQuizApiErrorResponse = toAutoQuizApiErrorResponse(res);

        throw new ApiException(
                HttpStatus.valueOf(statusCode.value()),
                autoQuizApiErrorResponse.getMessage(),
                autoQuizApiErrorResponse.getData()
        );
    }

    private AutoQuizApiErrorResponse toAutoQuizApiErrorResponse(ClientHttpResponse res) {
        try {
            String responseBody = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
            return objectMapper.readValue(responseBody, AutoQuizApiErrorResponse.class);
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }
}
