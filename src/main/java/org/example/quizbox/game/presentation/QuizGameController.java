package org.example.quizbox.game.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.auth.auth.AccessUser;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.game.application.QuizGameService;
import org.example.quizbox.game.domain.QuizGameId;
import org.example.quizbox.game.domain.QuizGameStatus;
import org.example.quizbox.game.domain.SubmitAnswer;
import org.example.quizbox.quiz.domain.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/quiz-game")
@RequiredArgsConstructor
public class QuizGameController {

    private final QuizGameService quizGameService;

    @GetMapping("/on-going")
    public ResponseEntity<BasicResponse<OnGoingQuizGameResponse>> getOnGoingQuizGames() {
        boolean randomBoolean = new Random().nextBoolean();
        if (randomBoolean) {
            return ResponseEntity.ok(new BasicResponse<>(OnGoingQuizGameResponse.dummy()));
        }

        return ResponseEntity.ok(new BasicResponse<>(null));
    }

    @PostMapping
    public ResponseEntity<BasicResponse<QuizGameStatusResponse>> start(
            @RequestBody StartQuizRequest request,
            AccessUser accessUser
    ) {
        QuizGameStatus status = quizGameService.startGame(request.toStartQuiz(accessUser.getId()));

        return ResponseEntity.ok(new BasicResponse<>(QuizGameStatusResponse.from(status)));
    }

    @PostMapping("/{quiz-game-id}/next-quiz")
    public ResponseEntity<BasicResponse<Quiz>> pick(
            @PathVariable("quiz-game-id") String quizGameId,
            AccessUser accessUser
    ) {
        Quiz quiz = quizGameService.pick(accessUser.getId(), QuizGameId.from(quizGameId))
                .orElseGet(() -> null);

        return ResponseEntity.ok(new BasicResponse<>(quiz));
    }

    @PostMapping("/{quiz-game-id}/submission")
    public ResponseEntity<BasicResponse<Void>> submit(
            @PathVariable("quiz-game-id") String quizGameId,
            @RequestBody SubmitAnswerRequest submitAnswerRequest,
            AccessUser accessUser
    ) {
        quizGameService.submit(
                accessUser.getId(),
                QuizGameId.from(quizGameId),
                new SubmitAnswer(submitAnswerRequest.answerIds())
        );

        return ResponseEntity.ok(new BasicResponse<>(null));
    }
//
//    @GetMapping("/{quiz-game-id}")
//    public ResponseEntity<BasicResponse<QuizGameStatusResponse>> status(
//            @PathVariable("quiz-game-id") String quizGameId) {
//        QuizGameStatus status = quizGameService.status(QuizGameId.from(quizGameId));
//
//        return ResponseEntity.ok(new BasicResponse<>(QuizGameStatusResponse.from(status)));
//    }

}
