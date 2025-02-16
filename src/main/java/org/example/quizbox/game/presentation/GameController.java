package org.example.quizbox.game.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.presentation.CommonResponse;
import org.example.quizbox.game.application.GameQuizResponse;
import org.example.quizbox.game.application.GameService;
import org.example.quizbox.game.application.GameStatusResponse;
import org.example.quizbox.game.domain.GameHistory;
import org.example.quizbox.game.domain.GameId;
import org.example.quizbox.game.domain.SubmitOption;
import org.example.quizbox.game.domain.TodayGameContributions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/in-progress")
    public ResponseEntity<CommonResponse<Set<InProgressQuizGameResponse>>> getOnGoingQuizGames(
            @RequestHeader("X-USER-ID") long userId
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "진행중인 게임 조회 성공",
                        gameService.getInProgressQuizGames(userId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<CommonResponse<GameStatusResponse>> start(
            @RequestHeader("X-USER-ID") long userId,
            @RequestBody StartQuizRequest request

    ) {
        GameStatusResponse status = gameService.start(request.toStartQuiz(userId));

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "게임 시작 성공",
                        status
                )
        );
    }

    @PostMapping("/{game-id}/next-quiz")
    public ResponseEntity<CommonResponse<GameQuizResponse>> pick(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("game-id") String quizGameId

    ) {
        GameQuizResponse pick = gameService.pick(userId, GameId.from(quizGameId));

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈 뽑기 성공",
                        pick
                )
        );
    }

    @PostMapping("/{game-id}/submission")
    public ResponseEntity<CommonResponse<Void>> submit(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("game-id") String quizGameId,
            @RequestBody SubmitOptionRequest submitOptionRequest

    ) {
        gameService.submit(
                userId,
                GameId.from(quizGameId),
                new SubmitOption(submitOptionRequest.optionIds())
        );

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "답변 제출 성공",
                        null
                )
        );
    }

    @GetMapping("/{game-id}/result")
    public ResponseEntity<CommonResponse<GameHistory>> getGameResult(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("game-id") String quizGameId
    ) {
        GameHistory gameResult = gameService.getGameResult(GameId.from(quizGameId));

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "게임 결과 조회 성공",
                        gameResult
                )
        );
    }

    @GetMapping("/contributions")
    public ResponseEntity<CommonResponse<List<TodayGameContributions>>> getGameContributions(
            @RequestHeader("X-USER-ID") long userId
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "잔디 조회 성공",
                        gameService.getGameContributions(userId)
                )
        );
    }
}
