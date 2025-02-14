package org.example.quizbox.game.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.game.application.GameQuizResponse;
import org.example.quizbox.game.application.GameService;
import org.example.quizbox.game.application.GameStatusResponse;
import org.example.quizbox.game.domain.GameHistory;
import org.example.quizbox.game.domain.GameId;
import org.example.quizbox.game.domain.SubmitOption;
import org.example.quizbox.game.domain.TodayGameContributions;
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
    public ResponseEntity<BasicResponse<Set<InProgressQuizGameResponse>>> getOnGoingQuizGames(
            @RequestParam(value = "user-id") Long userId

    ) {
        return ResponseEntity.ok(new BasicResponse<>(gameService.getInProgressQuizGames(userId)));
    }

    @PostMapping
    public ResponseEntity<BasicResponse<GameStatusResponse>> start(
            @RequestBody StartQuizRequest request,
            @RequestParam(value = "user-id") Long userId

    ) {
        GameStatusResponse status = gameService.start(request.toStartQuiz(userId));

        return ResponseEntity.ok(new BasicResponse<>(status));
    }

    @PostMapping("/{game-id}/next-quiz")
    public ResponseEntity<BasicResponse<GameQuizResponse>> pick(
            @PathVariable("game-id") String quizGameId,
            @RequestParam(value = "user-id") Long userId

    ) {
        GameQuizResponse pick = gameService.pick(userId, GameId.from(quizGameId));

        return ResponseEntity.ok(new BasicResponse<>(pick));
    }

    @PostMapping("/{game-id}/submission")
    public ResponseEntity<BasicResponse<Void>> submit(
            @PathVariable("game-id") String quizGameId,
            @RequestBody SubmitOptionRequest submitOptionRequest,
            @RequestParam(value = "user-id") Long userId

    ) {
        gameService.submit(
                userId,
                GameId.from(quizGameId),
                new SubmitOption(submitOptionRequest.optionIds())
        );

        return ResponseEntity.ok(new BasicResponse<>(null));
    }

    @GetMapping("/{game-id}/result")
    public ResponseEntity<BasicResponse<GameHistory>> getGameResult(
            @PathVariable("game-id") String quizGameId,
            @RequestParam(value = "user-id") Long userId

    ) {
        GameHistory gameResult = gameService.getGameResult(GameId.from(quizGameId));

        return ResponseEntity.ok(new BasicResponse<>(gameResult));
    }

    @GetMapping("/contributions")
    public ResponseEntity<BasicResponse<List<TodayGameContributions>>> getGameContributions(
            @RequestParam(value = "user-id") Long userId

    ) {
        return ResponseEntity.ok(new BasicResponse<>(gameService.getGameContributions(userId)));
    }
}
