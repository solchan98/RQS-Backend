package com.example.autoquizbox.presentation;

import com.example.autoquizbox.common.CommonResponse;
import com.example.autoquizbox.entities.AutoQuizPack;
import com.example.autoquizbox.entities.AutoQuizTaskHistory;
import com.example.autoquizbox.entities.TaskStatus;
import com.example.autoquizbox.service.command.*;
import com.example.autoquizbox.service.query.AutoQuizQueryService;
import com.example.autoquizbox.service.query.AutoQuizTaskHistoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auto")
@RequiredArgsConstructor
public class AutoQuizController {

    private final AutoQuizTaskHistoryQueryService autoQuizTaskHistoryQueryService;
    private final AutoQuizQueryService autoQuizQueryService;

    private final AutoQuizTaskAddCommandService autoQuizTaskAddCommandService;
    private final AutoQuizPackPublishCommandService autoQuizPackPublishCommandService;
    private final AutoQuizTaskHistoryCheckCommandService autoQuizTaskHistoryCheckCommandService;
    private final AutoQuizPackUpdateCommandService autoQuizPackUpdateCommandService;

    @PostMapping
    public ResponseEntity<CommonResponse<AutoQuizTaskHistory>> addTask(
            @RequestHeader("X-USER-ID") long userId,
            @RequestBody AddTaskRequest addTaskRequest) {

        AutoQuizTaskHistory autoQuizTaskHistory = autoQuizTaskAddCommandService.command(userId, addTaskRequest);

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "자동 퀴즈 생성 작업 추가 성공",
                        autoQuizTaskHistory
                )
        );
    }

    @PostMapping("/pause")
    public ResponseEntity<CommonResponse<Void>> pause() {
        autoQuizTaskAddCommandService.pause();
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "SUCCESS",
                        null
                )
        );
    }

    @PostMapping("/resume")
    public ResponseEntity<CommonResponse<Void>> resume() {
        autoQuizTaskAddCommandService.resume();
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "SUCCESS",
                        null
                )
        );
    }

    @GetMapping("/task/user/{user-id}")
    public ResponseEntity<CommonResponse<List<AutoQuizTaskStatus>>> getTaskStatus(
            @PathVariable("user-id") long userId,
            @RequestParam("task-statuses") Set<TaskStatus> taskStatuses
    ) {

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "작업 상태 조회 성공",
                        autoQuizTaskHistoryQueryService.query(userId, new ArrayList<>(taskStatuses))
                )
        );
    }

    @GetMapping("/task/{task-id}/quiz-pack")
    public ResponseEntity<CommonResponse<AutoQuizPack>> getAutoQuizPack(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("task-id") long taskId
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "오토 퀴즈팩 조회 성공",
                        autoQuizQueryService.query(userId, taskId)
                )
        );
    }

    @PutMapping("/auto-quiz-pack")
    public ResponseEntity<CommonResponse<AutoQuizPack>> commandUpdateAutoQuizPack(
            @RequestHeader("X-USER-ID") long userId,
            @RequestBody AutoQuizPack autoQuizPack
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "오토 퀴즈팩 수정 성공",
                        autoQuizPackUpdateCommandService.command(userId, autoQuizPack)
                )
        );
    }

    @PostMapping("/auto-quiz-pack/{auto-quiz-pack-id}/quiz-pack")
    public ResponseEntity<CommonResponse<Long>> queryPublishAutoQuizPack(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("auto-quiz-pack-id") long autoQuizPackId
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 발행 성공",
                        autoQuizPackPublishCommandService.command(userId, autoQuizPackId)
                )
        );
    }

    @PostMapping("/task/{task-id}/check")
    public ResponseEntity<CommonResponse<Boolean>> queryCheckTask(
            @RequestHeader("X-USER-ID") long userId,
            @PathVariable("task-id") long taskId
    ) {
        autoQuizTaskHistoryCheckCommandService.command(userId, taskId);

        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "작업 확인 성공",
                        true
                )
        );
    }
}
