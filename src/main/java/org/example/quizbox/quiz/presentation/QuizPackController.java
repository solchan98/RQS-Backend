package org.example.quizbox.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.infrastructure.Pagination;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.quiz.application.CreateQuizPack;
import org.example.quizbox.quiz.application.CreateSimpleQuizPack;
import org.example.quizbox.quiz.application.QuizPackResponse;
import org.example.quizbox.quiz.application.QuizPackService;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz-packs")
public class QuizPackController {

    private final QuizPackService quizPackService;

    @GetMapping
    public ResponseEntity<BasicResponse<List<QuizPackStatusResponse>>> getQuizPacks(
            @RequestHeader("X-USER-ID") long userId,
            @RequestParam(value = "lastId", required = false) Long lastId,
            @RequestParam(value = "chunk") int chunk,
            @RequestParam(value = "searchType") QuizPackSearchType searchType
    ) {
        Pagination pagination = new Pagination(lastId, chunk, false);
        Long memberId = searchType == QuizPackSearchType.MY ? userId : null;
        List<QuizPackStatusResponse> quizPackStatusResponses = quizPackService.getQuizPacks(memberId,
                        pagination)
                .stream()
                .map(QuizPackStatusResponse::from)
                .toList();

        return ResponseEntity.ok(new BasicResponse<>(quizPackStatusResponses));
    }

    @GetMapping("/{quiz-pack-id}")
    public ResponseEntity<BasicResponse<QuizPackResponse>> getQuizPackStatus(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("quiz-pack-id") long quizPackId
    ) {

        return ResponseEntity.ok(new BasicResponse<>(quizPackService.getQuizPack(quizPackId, userId)));
    }

    @PostMapping
    public long createQuizPack(
            @RequestHeader("X-USER-ID") long userId,
            @RequestBody CreateQuizPack createQuizPack

    ) {
        QuizPack quizPack = quizPackService.create(userId, createQuizPack.title(), createQuizPack.tagIds());

        return quizPack.getId();
    }

    @PostMapping("/simple")
    public ResponseEntity<BasicResponse<Long>> createSimpleQuizPack(
            @RequestHeader("X-USER-ID") long userId,
            @RequestBody CreateSimpleQuizPack simpleQuizPack

    ) {
        QuizPack quizPack = quizPackService.create(userId, simpleQuizPack);

        return ResponseEntity.ok(new BasicResponse<>(quizPack.getId()));
    }

    @PostMapping("/{quiz-pack-id}/quiz")
    public long createQuiz(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("quiz-pack-id") long quizPackId,
            @RequestBody CreateQuizRequest request

    ) {
        return quizPackService.addQuiz(request.toCreateQuiz(quizPackId, userId));
    }

    @GetMapping("/{quiz-pack-id}/quizzes")
    public ResponseEntity<BasicResponse<Set<QuizResponse>>> getQuiz(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("quiz-pack-id") long quizPackId
    ) {
        Set<Quiz> quizzes = quizPackService.getQuizzes(quizPackId, userId);

        Set<QuizResponse> quizResponses = quizzes.stream()
                .map(quiz -> QuizResponse.from(quiz, true))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new BasicResponse<>(quizResponses));
    }
}
