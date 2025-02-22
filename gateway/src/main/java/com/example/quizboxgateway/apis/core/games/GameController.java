package com.example.quizboxgateway.apis.core.games;

import com.example.quizboxgateway.auth.auth.AccessUser;
import com.example.quizboxgateway.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/core/games")
@RequiredArgsConstructor
public class GameController {

    private final GamesApi gamesApi;

    @GetMapping // /in-progress
    public ResponseEntity<CommonResponse<List<QueryInProgressGameResponse>>> getOnGoingQuizGames(AccessUser accessUser) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "진행중인 게임 조회 성공",
                        gamesApi.queryInProgressGames(accessUser.getId()).getData()
                )
        );
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CommandStartGameResponse>> start(
            AccessUser accessUser,
            @RequestBody CommandStartGameRequest request

    ) {

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "게임 시작 성공",
                        gamesApi.commandStartGame(accessUser.getId(), request).getData()
                )
        );
    }


    @PostMapping("/{game-id}/submission")
    public ResponseEntity<CommonResponse<Void>> submit(
            AccessUser accessUser,
            @PathVariable("game-id") String gameId,
            @RequestBody CommandSubmitQuizRequest request
    ) {
        gamesApi.commandSubmitQuiz(accessUser.getId(), gameId, request);

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈 제출 성공",
                        null
                )
        );
    }

    @PostMapping("/{game-id}/next-quiz")
    public ResponseEntity<CommonResponse<QueryGameQuizResponse>> pick(
            AccessUser accessUser,
            @PathVariable("game-id") String quizGameId
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈 뽑기 성공",
                        gamesApi.commandNextQuiz(accessUser.getId(), quizGameId).getData()
                )
        );
    }


    @GetMapping("/{game-id}/result")
    public ResponseEntity<CommonResponse<QueryGameHistoryResponse>> getGameResult(
            AccessUser accessUser,
            @PathVariable("game-id") String quizGameId
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "게임 결과 조회 성공",
                        gamesApi.queryGameResult(accessUser.getId(), quizGameId).getData()
                )
        );
    }

    @GetMapping("/contributions")
    public ResponseEntity<CommonResponse<List<QueryGameContributionResponse>>> getGameContributions(
            AccessUser accessUser
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "잔디 조회 성공",
                        gamesApi.queryGameContributions(accessUser.getId()).getData()
                )
        );
    }
}
