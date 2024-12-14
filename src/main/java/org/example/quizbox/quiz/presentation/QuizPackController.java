package org.example.quizbox.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.auth.auth.AccessUser;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.common.presentation.Pagination;
import org.example.quizbox.common.presentation.PaginationResponse;
import org.example.quizbox.quiz.application.CreateQuizPack;
import org.example.quizbox.quiz.domain.QuizPackStatus;
import org.example.quizbox.quiz.application.QuizPackService;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz-packs")
public class QuizPackController {

    private final QuizPackService quizPackService;

    @GetMapping
    public ResponseEntity<PaginationResponse<List<QuizPackStatusResponse>>> getQuizPacks(
            @RequestParam(value = "lastId", required = false) Long lastId,
            @RequestParam(value = "chunk") long chunk
    ) {
        Pagination pagination = new Pagination(20, 20, true);
        List<QuizPackStatusResponse> dummy = QuizPackStatusResponse.dummy();

        return ResponseEntity.ok(new PaginationResponse<>(pagination, dummy));
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
        QuizPack quizPack = quizPackService.create(accessUser.getId(), createQuizPack.title(), createQuizPack.tagIds());

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

//    @GetMapping("/{quiz-pack-id}/quiz/{quiz-id}")
//    public ResponseEntity<BasicResponse<QuizResponse>> getQuiz(
//            @PathVariable("quiz-pack-id") long quizPackId,
//            @PathVariable("quiz-id") long quizId,
//            AccessUser accessUser
//    ) {
//        Quiz quiz = quizPackService.getQuiz(quizPackId, quizId, accessUser.getId());
//
//        return ResponseEntity.ok(new BasicResponse<>(QuizResponse.from(quiz)));
//    }
}
