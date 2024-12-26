package org.example.quizbox.game.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.auth.auth.AccessUser;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.game.application.GameQuizResponse;
import org.example.quizbox.game.application.GameService;
import org.example.quizbox.game.application.GameStatusResponse;
import org.example.quizbox.game.domain.GameId;
import org.example.quizbox.game.domain.SubmitOption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/in-progress")
    public ResponseEntity<BasicResponse<Set<InProgressQuizGameResponse>>> getOnGoingQuizGames(
            AccessUser accessUser
    ) {
        return ResponseEntity.ok(new BasicResponse<>(gameService.getInProgressQuizGames(accessUser.getId())));
    }

    @PostMapping
    public ResponseEntity<BasicResponse<GameStatusResponse>> start(
            @RequestBody StartQuizRequest request,
            AccessUser accessUser
    ) {
        GameStatusResponse status = gameService.start(request.toStartQuiz(accessUser.getId()));

        return ResponseEntity.ok(new BasicResponse<>(status));
    }

    @PostMapping("/{game-id}/next-quiz")
    public ResponseEntity<BasicResponse<GameQuizResponse>> pick(
            @PathVariable("game-id") String quizGameId,
            AccessUser accessUser
    ) {
        GameQuizResponse pick = gameService.pick(accessUser.getId(), GameId.from(quizGameId));

        return ResponseEntity.ok(new BasicResponse<>(pick));
    }

    @PostMapping("/{game-id}/submission")
    public ResponseEntity<BasicResponse<Void>> submit(
            @PathVariable("game-id") String quizGameId,
            @RequestBody SubmitOptionRequest submitOptionRequest,
            AccessUser accessUser
    ) {
        gameService.submit(
                accessUser.getId(),
                GameId.from(quizGameId),
                new SubmitOption(submitOptionRequest.optionIds())
        );

        return ResponseEntity.ok(new BasicResponse<>(null));
    }
}
