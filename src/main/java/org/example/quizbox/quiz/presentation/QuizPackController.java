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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz-packs")
public class QuizPackController {

    private final QuizPackService quizPackService;

    @GetMapping("/recommendation")
    public ResponseEntity<BasicResponse<RecommendationQuizPackResponse<?>>> getRecommendationQuizPacks(
            @RequestParam("recommendation-type") String recommendationType
    ) {
        if (recommendationType.equals("LEARNING_DAYS")) {
            return ResponseEntity.ok(new BasicResponse<>(RecommendationQuizPackResponse.dummyLearningDays()));
        }

        return ResponseEntity.ok(new BasicResponse<>(RecommendationQuizPackResponse.dummyOthers()));
    }

    @GetMapping("/{quiz-pack-id}/status")
    public ResponseEntity<BasicResponse<QuizPackStatusResponse>> getQuizPackStatus(
            @PathVariable("quiz-pack-id") long quizPackId,
            AccessUser accessUser
    ) {
        QuizPackStatus status = quizPackService.getQuizPack(quizPackId, accessUser.getId());

        return ResponseEntity.ok(new BasicResponse<>(QuizPackStatusResponse.from(status)));
    }
//
    @PostMapping
    public long createQuizPack(
            @RequestBody CreateQuizPack createQuizPack,
            AccessUser accessUser
    ) {
        QuizPack quizPack = quizPackService.create(createQuizPack.title(), accessUser.getId());

        return quizPack.getId();
    }

    @PostMapping("/{quiz-pack-id}/quiz")
    public long createQuiz(
            @PathVariable("quiz-pack-id") long quizPackId,
            @RequestBody CreateQuizRequest request,
            AccessUser accessUser
    ) {
        return quizPackService.addQuiz(request.toCreateQuiz(quizPackId, accessUser.getId()));
    }

    @GetMapping("/{quiz-pack-id}/quiz/{quiz-id}")
    public ResponseEntity<BasicResponse<QuizResponse>> getQuiz(
            @PathVariable("quiz-pack-id") long quizPackId,
            @PathVariable("quiz-id") long quizId,
            AccessUser accessUser
    ) {
        Quiz quiz = quizPackService.getQuiz(quizPackId, quizId, accessUser.getId());

        return ResponseEntity.ok(new BasicResponse<>(QuizResponse.from(quiz)));
    }
}
