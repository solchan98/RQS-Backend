package org.example.quizbox.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.auth.auth.AccessUser;
import org.example.quizbox.common.infrastructure.Pagination;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.quiz.application.AddQuizAutoCreateTask;
import org.example.quizbox.quiz.application.CreateQuizPack;
import org.example.quizbox.quiz.application.QuizPackResponse;
import org.example.quizbox.quiz.application.QuizPackService;
import org.example.quizbox.quiz.domain.AddQuizAutoCreateTaskResult;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.quizbox.quiz.domain.AddQuizAutoCreateTaskResult.AddTaskStatus.FAILED;

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

    @PostMapping
    public long createQuizPack(
            @RequestBody CreateQuizPack createQuizPack,
            AccessUser accessUser
    ) {
        QuizPack quizPack = quizPackService.create(accessUser.getId(), createQuizPack.title(), createQuizPack.tagIds());

        return quizPack.getId();
    }

    @PostMapping("/auto")
    public ResponseEntity<BasicResponse<AddQuizAutoCreateTaskResult>> createQuizPackAuto(
            AccessUser accessUser,
            @RequestBody AddQuizAutoCreateTask addQuizAutoCreateTask
    ) {
        AddQuizAutoCreateTaskResult addQuizAutoCreateTaskResult = quizPackService.addAutoCreateTask(accessUser.getId(),
                addQuizAutoCreateTask);

        HttpStatus httpStatus = HttpStatus.CREATED;
        if (addQuizAutoCreateTaskResult.getStatus() == FAILED) {
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        }

        return ResponseEntity.status(httpStatus).body(new BasicResponse<>(addQuizAutoCreateTaskResult));
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
