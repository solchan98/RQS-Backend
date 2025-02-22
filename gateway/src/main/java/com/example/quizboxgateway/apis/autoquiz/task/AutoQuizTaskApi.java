package com.example.quizboxgateway.apis.autoquiz.task;

import com.example.quizboxgateway.apis.autoquiz.AutoQuizApi;
import com.example.quizboxgateway.apis.autoquiz.AutoQuizApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;

@Component
public class AutoQuizTaskApi extends AutoQuizApi {

    private final RestClient autoQuizClient;

    protected AutoQuizTaskApi(@Qualifier("autoQuizClient") RestClient autoQuizClient, ObjectMapper objectMapper) {
        super(objectMapper);
        this.autoQuizClient = autoQuizClient;
    }

    public AutoQuizApiResponse<List<QueryAutoQuizTaskStatusResponse>> queryAutoQuizTaskStatuses(
            long userId,
            Set<String> taskStatuses
    ) {
        MultiValueMap<String, String> taskStatusesQueryParams = new LinkedMultiValueMap<>();
        taskStatuses.forEach(taskStatus -> taskStatusesQueryParams.add("task-statuses", taskStatus));

        return autoQuizClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/auto/task/user/")
                        .path(String.valueOf(userId))
                        .queryParams(taskStatusesQueryParams)
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<List<QueryAutoQuizTaskStatusResponse>>>() {
                })
                .getBody();
    }

    public AutoQuizApiResponse<CommandAddAutoQuizTaskResponse> commandAddAutoQuizTask(
            long userId,
            CommandAddAutoQuizTaskRequest request
    ) {
        return autoQuizClient.post()
                .uri("/auto")
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<CommandAddAutoQuizTaskResponse>>() {
                })
                .getBody();
    }

    public AutoQuizApiResponse<Boolean> commandTaskCheck(long userId, long taskId) {
        return autoQuizClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/auto/task/")
                        .path(String.valueOf(taskId))
                        .path("/check")
                        .build()
                )
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<Boolean>>() {
                })
                .getBody();
    }
}
