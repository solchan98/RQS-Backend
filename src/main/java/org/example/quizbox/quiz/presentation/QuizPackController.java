package org.example.quizbox.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.auth.auth.AccessUser;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.quiz.application.CreateQuizPack;
import org.example.quizbox.quiz.application.QuizPackService;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz-pack")
public class QuizPackController {

    private final QuizPackService quizPackService;

    @GetMapping("/{quiz-pack-id}/status")
    public ResponseEntity<BasicResponse<QuizPackStatusResponse>> getQuizPackStatus(
            @PathVariable("quiz-pack-id") long quizPackId,
            AccessUser accessUser
    ) {
        QuizPackStatus status = quizPackService.getQuizPack(quizPackId, accessUser.getId());

        return ResponseEntity.ok(new BasicResponse<>(QuizPackStatusResponse.from(status)));
    }

    @PostMapping
    public long createQuizPack(@RequestBody CreateQuizPack createQuizPack) {
        long memberId = 1;
        QuizPack quizPack = quizPackService.create(createQuizPack.title(), memberId);

        return quizPack.getId();
    }

    @PostMapping("/{quiz-pack-id}/quiz")
    public long createQuiz(@PathVariable("quiz-pack-id") long quizPackId, @RequestBody CreateQuizRequest request) {
        long memberId = 1;
        Quiz quiz = quizPackService.createQuiz(request.toCreateQuiz(memberId, quizPackId));

        return quiz.getId();
    }

    @GetMapping("/{quiz-pack-id}/quiz/{quiz-id}")
    public ResponseEntity<BasicResponse<QuizResponse>> getQuiz(@PathVariable("quiz-pack-id") long quizPackId,
            @PathVariable("quiz-id") long quizId) {
        Quiz quiz = quizPackService.getQuiz(quizPackId, quizId);

        return ResponseEntity.ok(new BasicResponse<>(QuizResponse.from(quiz)));
    }
}
