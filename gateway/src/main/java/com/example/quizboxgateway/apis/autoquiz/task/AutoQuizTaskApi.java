package com.example.quizboxgateway.apis.autoquiz.task;

import com.example.quizboxgateway.apis.autoquiz.AutoQuizApi;
import com.example.quizboxgateway.apis.autoquiz.AutoQuizApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
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

    public AutoQuizApiResponse<List<QueryAutoTaskStatusResponse>> queryAutoQuizTaskStatuses(
            long userId,
            Set<String> taskStatuses
    ) {
        MultiValueMap<String, String> taskStatusesQueryParams = new LinkedMultiValueMap<>();
        if (!CollectionUtils.isEmpty(taskStatuses)) {
            taskStatuses.forEach(taskStatus -> taskStatusesQueryParams.add("task-statuses", taskStatus));
        }

        return autoQuizClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/auto-task")
                        .queryParams(taskStatusesQueryParams)
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<List<QueryAutoTaskStatusResponse>>>() {
                })
                .getBody();
    }


    /**
     * 퀴즈팩 자동 생성 작업 추가
     *
     * @param userId  유저 아이디
     * @param request 퀴즈팩 자동 생성 데이터
     */
    public AutoQuizApiResponse<QueryAutoTaskStatusResponse> commandAddAutoQuizTask(
            long userId,
            CommandAddAutoQuizTaskRequest request
    ) {
        return autoQuizClient.post()
                .uri("/auto-task")
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<QueryAutoTaskStatusResponse>>() {
                })
                .getBody();
    }

    /**
     * 퀴즈팩 작업 확인
     * PENDING_REVIEW : 에러 확인
     * WAITING_TO_BE_PUBLISHED : 프리뷰 퀴즈팩 생성
     *
     * @param userId 유저 아이디
     * @param taskId 작업 id
     */
    public AutoQuizApiResponse<CommandTaskConfirmResponse> commandTaskConfirm(long userId, long taskId) {
        return autoQuizClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/auto-task/")
                        .path("/confirm/")
                        .path(String.valueOf(taskId))
                        .build()
                )
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<AutoQuizApiResponse<CommandTaskConfirmResponse>>() {
                })
                .getBody();
    }
}
