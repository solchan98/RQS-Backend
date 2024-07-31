package org.example.quizbox.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.quiz.application.CreateQuizPack;
import org.example.quizbox.quiz.application.QuizPackService;
import org.example.quizbox.quiz.application.QuizPackStatus;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
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
    public ResponseEntity<BasicResponse<QuizPackStatus>> getQuizPackStatus(
            @PathVariable("quiz-pack-id") long quizPackId) {
        QuizPackStatus quizPackStatus = quizPackService.getQuizPackStatus(quizPackId);

        return ResponseEntity.ok(new BasicResponse<>(quizPackStatus));
    }

    @PostMapping
    public long createQuizPack(@RequestBody CreateQuizPack createQuizPack) {

        QuizPack quizPack = quizPackService.create(createQuizPack.title());

        return quizPack.getId();
    }

    @PostMapping("/{quiz-pack-id}/quiz")
    public long createQuiz(@PathVariable("quiz-pack-id") long quizPackId, @RequestBody CreateQuizRequest request) {
        Quiz quiz = quizPackService.addQuiz(request.toCreateQuiz(quizPackId));

        return quiz.getId();
    }
}
