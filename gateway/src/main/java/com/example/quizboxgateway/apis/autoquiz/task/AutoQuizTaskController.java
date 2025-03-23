package com.example.quizboxgateway.apis.autoquiz.task;

import com.example.quizboxgateway.auth.domain.AccessUser;
import com.example.quizboxgateway.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/auto-task")
@RequiredArgsConstructor
public class AutoQuizTaskController {

    private final AutoQuizTaskApi autoQuizTaskApi;

    @PostMapping
    public ResponseEntity<CommonResponse<QueryAutoTaskStatusResponse>> commandAddTask(
            AccessUser accessUser,
            @RequestBody CommandAddAutoQuizTaskRequest request
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 자동 생성 작업 추가 성공",
                        autoQuizTaskApi.commandAddAutoQuizTask(accessUser.getId(), request).getData()
                )
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<QueryAutoTaskStatusResponse>>> queryTaskStatus(
            AccessUser accessUser,
            @RequestParam(value = "task-statuses", required = false) Set<String> taskStatuses
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 자동 생성 작업 조회 성공",
                        autoQuizTaskApi.queryAutoQuizTaskStatuses(accessUser.getId(), taskStatuses).getData()
                )
        );
    }

    @PostMapping("confirm/{task-id}")
    public ResponseEntity<CommonResponse<CommandTaskConfirmResponse>> commandTaskCheck(
            AccessUser accessUser,
            @PathVariable("task-id") long taskId
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 작업 확인 성공",
                        autoQuizTaskApi.commandTaskConfirm(accessUser.getId(), taskId).getData()
                )
        );

    }

//    @PostMapping("/task/{task-id}/check")
//    public ResponseEntity<CommonResponse<Void>> queryCheckTask(@PathVariable("task-id") long taskId) {
//
//
//    }
//
//    @PostMapping("/pause")
//    public ResponseEntity<BasicResponse<String>> pause() {
//        addAutoQuizTaskService.pause();
//        return ResponseEntity.ok(new BasicResponse<>("SUCCESS"));
//    }
//
//    @PostMapping("/resume")
//    public ResponseEntity<BasicResponse<String>> resume() {
//        addAutoQuizTaskService.resume();
//        return ResponseEntity.ok(new BasicResponse<>("SUCCESS"));
//    }
//
}
