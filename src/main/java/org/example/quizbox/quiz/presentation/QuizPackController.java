package org.example.quizbox.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.Pagination;
import org.example.quizbox.common.CommonResponse;
import org.example.quizbox.quiz.application.CreateSimpleQuizPack;
import org.example.quizbox.quiz.application.QuizPackResponse;
import org.example.quizbox.quiz.application.QuizPackService;
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
    public ResponseEntity<CommonResponse<List<QuizPackStatusResponse>>> getQuizPacks(
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

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 리스트 조회 성공",
                        quizPackStatusResponses
                )
        );
    }

    @GetMapping("/{quiz-pack-id}")
    public ResponseEntity<CommonResponse<QuizPackResponse>> getQuizPackStatus(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("quiz-pack-id") long quizPackId
    ) {

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 상세 조회 성공",
                        quizPackService.getQuizPack(quizPackId, userId)
                )
        );
    }

    /**
     * TODO: 퀴즈팩 수동 생성 기능 오픈 시, 사용
     */
//    @PostMapping
//    public long createQuizPack(
//            @RequestHeader("X-USER-ID") long userId,
//            @RequestBody CreateQuizPack createQuizPack
//
//    ) {
//        QuizPack quizPack = quizPackService.create(userId, createQuizPack.title(), createQuizPack.tagIds());
//
//        return quizPack.getId();
//    }

    /**
     * TODO: 퀴즈팩 수동 생성 기능 오픈 시, 사용
     */
//    @PostMapping("/{quiz-pack-id}/quiz")
//    public long createQuiz(
//            @RequestHeader("X-USER-ID") long userId,
//            @PathVariable("quiz-pack-id") long quizPackId,
//            @RequestBody CreateQuizRequest request
//
//    ) {
//        return quizPackService.addQuiz(request.toCreateQuiz(quizPackId, userId));
//    }
    @PostMapping("/simple")
    public ResponseEntity<CommonResponse<Long>> createSimpleQuizPack(
            @RequestHeader("X-USER-ID") long userId,
            @RequestBody CreateSimpleQuizPack simpleQuizPack

    ) {
        QuizPack quizPack = quizPackService.create(userId, simpleQuizPack);

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 발행 성공",
                        quizPack.getId()
                )
        );
    }

    @GetMapping("/{quiz-pack-id}/quizzes")
    public ResponseEntity<CommonResponse<Set<QuizResponse>>> getQuiz(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("quiz-pack-id") long quizPackId
    ) {
        Set<Quiz> quizzes = quizPackService.getQuizzes(quizPackId, userId);

        Set<QuizResponse> quizResponses = quizzes.stream()
                .map(quiz -> QuizResponse.from(quiz, true))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈 리스트 조회",
                        quizResponses
                )
        );
    }
}
