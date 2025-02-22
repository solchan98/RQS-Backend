package com.example.quizboxgateway.apis.core;

import com.example.quizboxgateway.common.ApiException;
import com.example.quizboxgateway.common.ServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class CoreApi {

    protected static final String AUTHORIZATION_HEADER = "X-USER-ID";

    private final ObjectMapper objectMapper;

    protected CoreApi(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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
