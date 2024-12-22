package org.example.quizbox.quiz.presentation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.auth.auth.AccessUser;
import org.example.quizbox.common.infrastructure.Pagination;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.quiz.application.CreateQuizPack;
import org.example.quizbox.quiz.application.QuizPackResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            AccessUser accessUser
    ) {
        Pagination pagination = new Pagination(lastId, chunk, false);
        Long memberId = searchType == QuizPackSearchType.MY ? accessUser.getId() : null;
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
            AccessUser accessUser
    ) {

        return ResponseEntity.ok(new BasicResponse<>(quizPackService.getQuizPack(quizPackId, accessUser.getId())));
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

    @PostMapping("/auto")
    public long createQuizPackAuto(
            @RequestBody CreateQuizPack createQuizPack,
            @RequestParam("hopeCount") int hopeCount,
            AccessUser accessUser
    ) {
        return quizPackService.autoGenerator(accessUser.getId(), createQuizPack.title(), createQuizPack.tagIds(),
                hopeCount);
    }

    @PostMapping("/{quiz-pack-id}/quiz")
    public long createQuiz(
            @PathVariable("quiz-pack-id") long quizPackId,
            @RequestBody CreateQuizRequest request,
            AccessUser accessUser
    ) {
        return quizPackService.addQuiz(request.toCreateQuiz(quizPackId, accessUser.getId()));
    }

    @GetMapping("/{quiz-pack-id}/quizzes")
    public ResponseEntity<BasicResponse<Set<QuizResponse>>> getQuiz(
            @PathVariable("quiz-pack-id") long quizPackId,
            AccessUser accessUser
    ) {
        Set<Quiz> quizzes = quizPackService.getQuizzes(quizPackId, accessUser.getId());

        Set<QuizResponse> quizResponses = quizzes.stream()
                .map(QuizResponse::from)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new BasicResponse<>(quizResponses));
    }
}
