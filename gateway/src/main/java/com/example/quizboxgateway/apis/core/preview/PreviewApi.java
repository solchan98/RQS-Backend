package com.example.quizboxgateway.apis.core.preview;

import com.example.quizboxgateway.apis.core.CoreApi;
import com.example.quizboxgateway.apis.core.CoreApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class PreviewApi extends CoreApi {

    private final RestClient coreClient;

    public PreviewApi(@Qualifier("coreClient") RestClient coreClient, ObjectMapper objectMapper) {
        super(objectMapper);
        this.coreClient = coreClient;
    }

    /**
     * 프리뷰 조회
     *
     * @param userId            유저 아이디
     * @param previewQuizPackId 프리뷰 퀴즈팩 아이디
     */
    public CoreApiResponse<QueryPreviewResponse> queryPreviewResponse(long userId, long previewQuizPackId) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/preview/")
                        .path(String.valueOf(previewQuizPackId))
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<QueryPreviewResponse>>() {
                })
                .getBody();
    }

    /**
     * 프리뷰 리스트 조회
     *
     * @param userId 유저 아이디
     */
    public CoreApiResponse<List<QueryPreviewResponse>> queryAllPreviewResponse(long userId) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/preview")
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<List<QueryPreviewResponse>>>() {
                })
                .getBody();
    }

    /**
     * 프리뷰 저장
     *
     * @param userId  유저 아이디
     * @param request 프리뷰 데이터
     */
    public CoreApiResponse<QueryPreviewResponse> commandSavePreview(long userId, CommandPreviewRequest request) {
        return coreClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/preview")
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<QueryPreviewResponse>>() {
                })
                .getBody();
    }

    /**
     * 프리뷰 발행
     *
     * @param userId            유저 아이디
     * @param previewQuizPackId 프리뷰 퀴즈팩 id
     */
    public CoreApiResponse<Long> commandPublishPreview(long userId, long previewQuizPackId) {
        return coreClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/preview/publish/")
                        .path(String.valueOf(previewQuizPackId))
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<Long>>() {
                })
                .getBody();
    }
}
