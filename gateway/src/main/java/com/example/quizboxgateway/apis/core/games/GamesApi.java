package com.example.quizboxgateway.apis.core.games;

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
public class GamesApi extends CoreApi {

    private final RestClient coreClient;

    public GamesApi(@Qualifier("coreClient") RestClient coreClient, ObjectMapper objectMapper) {
        super(objectMapper);
        this.coreClient = coreClient;
    }

    public CoreApiResponse<List<QueryInProgressGameResponse>> queryInProgressGames(long userId) {
        return coreClient.get()
                .uri("/games/in-progress")
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<List<QueryInProgressGameResponse>>>() {
                })
                .getBody();
    }

    public CoreApiResponse<CommandStartGameResponse> commandStartGame(long userId, CommandStartGameRequest request) {
        return coreClient.post()
                .uri("/games")
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<CommandStartGameResponse>>() {
                })
                .getBody();
    }

    public void commandSubmitQuiz(long userId, String gameId, CommandSubmitQuizRequest request) {
        coreClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/games/")
                        .path(gameId)
                        .path("/submission")
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(CoreApiResponse.class)
                .getBody();
    }

    public CoreApiResponse<QueryGameQuizResponse> commandNextQuiz(long userId, String quizGameId) {
        return coreClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/games/")
                        .path(quizGameId)
                        .path("/next-quiz")
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<QueryGameQuizResponse>>() {
                })
                .getBody();
    }

    public CoreApiResponse<QueryGameHistoryResponse> queryGameResult(long userId, String quizGameId) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games/")
                        .path(quizGameId)
                        .path("/result")
                        .build())
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<QueryGameHistoryResponse>>() {
                })
                .getBody();
    }

    public CoreApiResponse<List<QueryGameContributionResponse>> queryGameContributions(long userId) {
        return coreClient.get()
                .uri("/games/contributions")
                .header(AUTHORIZATION_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    handleError(res);
                })
                .toEntity(new ParameterizedTypeReference<CoreApiResponse<List<QueryGameContributionResponse>>>() {
                })
                .getBody();
    }
}
