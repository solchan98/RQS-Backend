package org.example.quizbox.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.infrastructure.Pagination;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.quiz.application.*;
import org.example.quizbox.quiz.domain.AddQuizAutoCreateTaskResult;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.http.HttpStatus;
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
            @RequestParam(value = "lastId", required = false) Long lastId,
            @RequestParam(value = "chunk") int chunk,
            @RequestParam(value = "searchType") QuizPackSearchType searchType,
            @RequestParam(value = "user-id") Long userId
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
            @PathVariable("quiz-pack-id") long quizPackId,
            @RequestParam(value = "user-id") Long userId
    ) {

        return ResponseEntity.ok(new BasicResponse<>(quizPackService.getQuizPack(quizPackId, userId)));
    }

    @PostMapping
    public long createQuizPack(
            @RequestBody CreateQuizPack createQuizPack,
            @RequestParam(value = "user-id") Long userId

    ) {
        QuizPack quizPack = quizPackService.create(userId, createQuizPack.title(), createQuizPack.tagIds());

        return quizPack.getId();
    }

    @PostMapping("/simple")
    public long createSimpleQuizPack(
            @RequestBody CreateSimpleQuizPack simpleQuizPack,
            @RequestParam(value = "user-id") Long userId

    ) {
        QuizPack quizPack = quizPackService.create(userId, simpleQuizPack);

        return quizPack.getId();
    }

    @PostMapping("/{quiz-pack-id}/quiz")
    public long createQuiz(
            @PathVariable("quiz-pack-id") long quizPackId,
            @RequestBody CreateQuizRequest request,
            @RequestParam(value = "user-id") Long userId

    ) {
        return quizPackService.addQuiz(request.toCreateQuiz(quizPackId, userId));
    }

    @GetMapping("/{quiz-pack-id}/quizzes")
    public ResponseEntity<BasicResponse<Set<QuizResponse>>> getQuiz(
            @PathVariable("quiz-pack-id") long quizPackId,
            @RequestParam(value = "user-id") Long userId
    ) {
        Set<Quiz> quizzes = quizPackService.getQuizzes(quizPackId, userId);

        Set<QuizResponse> quizResponses = quizzes.stream()
                .map(quiz -> QuizResponse.from(quiz, true))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new BasicResponse<>(quizResponses));
    }
}
