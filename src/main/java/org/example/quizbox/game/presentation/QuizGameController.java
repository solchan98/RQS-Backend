package org.example.quizbox.game.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.game.application.QuizGameService;
import org.example.quizbox.game.application.StartQuiz;
import org.example.quizbox.game.domain.QuizGame;
import org.example.quizbox.game.domain.QuizGameId;
import org.example.quizbox.game.domain.SubmitAnswer;
import org.example.quizbox.quiz.domain.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz-game")
@RequiredArgsConstructor
public class QuizGameController {

    private final QuizGameService quizGameService;

    @PostMapping
    public ResponseEntity<BasicResponse<QuizGameResponse>> start(@RequestBody StartQuiz startQuiz) {
        QuizGame quizGame = quizGameService.startGame(startQuiz);

        return ResponseEntity.ok(new BasicResponse<>(QuizGameResponse.from(quizGame)));
    }

    @PostMapping("/{quiz-game-id}/next-quiz")
    public ResponseEntity<BasicResponse<Quiz>> pick(@PathVariable("quiz-game-id") String quizGameId) {
        long memberId = 1L;
        Quiz quiz = quizGameService.pick(memberId, QuizGameId.from(quizGameId))
                .orElseGet(() -> null);

        return ResponseEntity.ok(new BasicResponse<>(quiz));
    }

    @PostMapping("/{quiz-game-id}/submission/{quiz-id}")
    public ResponseEntity<BasicResponse<Void>> submit(
            @PathVariable("quiz-game-id") String quizGameId,
            @PathVariable("quiz-id") long quizId,
            @RequestBody SubmitAnswerRequest submitAnswerRequest
    ) {
        long memberId = 1L;

        quizGameService.submit(
                QuizGameId.from(quizGameId),
                new SubmitAnswer(memberId, quizId, submitAnswerRequest.answerIds())
        );

        return ResponseEntity.ok(new BasicResponse<>(null));
    }

}
