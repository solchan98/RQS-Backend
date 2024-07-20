package org.example.quizbox.presentation;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.application.GameService;
import org.example.quizbox.application.GameStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @PostMapping("/quiz-group/{group-id}")
    public ResponseEntity<BasicResponse<Map<String, String>>> startGame(@PathVariable("group-id") long groupId) {
        long userId = 1;
        return ResponseEntity.ok(new BasicResponse<>(Map.of("gameKey", gameService.startGame(userId, groupId))));
    }

    @GetMapping("{game-id}")
    public ResponseEntity<BasicResponse<GameStatus>> gameStatus(@PathVariable("game-id") String gameId) {
        return ResponseEntity.status(200).body(new BasicResponse<>(gameService.gameStatus(gameId)));
    }
}
