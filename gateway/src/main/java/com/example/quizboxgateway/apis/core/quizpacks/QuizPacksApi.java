package com.example.quizboxgateway.apis.core.quizpacks;

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
public class QuizPacksApi extends CoreApi {

    private final RestClient coreClient;

    public QuizPacksApi(@Qualifier("coreClient") RestClient coreClient, ObjectMapper objectMapper) {
        super(objectMapper);
        this.coreClient = coreClient;
    }

    /**
     * 퀴즈팩 리스트 조회
     *
     * @param userId     유저 아이디
     * @param lastId     마지막 요소 아이디
     * @param chunk      페이징 단위
     * @param searchType ALL | MY
     */
    public CoreApiResponse<List<QueryQuizPacksResponse>> queryQuizPacks(long userId, Long lastId, int chunk, String searchType) {

        return coreClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/quiz-packs")
                        .queryParam("lastId", lastId)
                        .queryParam("chunk", chunk)
                        .queryParam("searchType", searchType)
                        .queryParam("user-id", userId)
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<List<QueryQuizPacksResponse>>>() {
                }) // 제네릭 타입을 명시적으로 처리
                .getBody();
    }

    /**
     * 퀴즈팩 디테일 조회
     *
     * @param userId     유저 아이디
     * @param quizPackId 퀴즈팩 아이디
     */
    public CoreApiResponse<QueryQuizPackDetailResponse> queryQuizPackDetails(long userId, long quizPackId) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/quiz-packs/")
                        .path(String.valueOf(quizPackId))
                        .queryParam("user-id", userId)
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<QueryQuizPackDetailResponse>>() {
                })
                .getBody();
    }

    /**
     * 퀴즈팩 퀴즈 조회
     *
     * @param userId     유저 아이디
     * @param quizPackId 퀴즈팩 아이디
     */
    public CoreApiResponse<List<QueryQuizzesResponse>> queryQuizzes(long userId, long quizPackId) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/quiz-packs/")
                        .path(String.valueOf(quizPackId))
                        .path("/quizzes")
                        .queryParam("user-id", userId)
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<List<QueryQuizzesResponse>>>() {
                })
                .getBody();
    }
}
