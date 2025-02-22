package com.example.quizboxgateway.apis.autoquiz.core;

import com.example.quizboxgateway.auth.auth.AccessUser;
import com.example.quizboxgateway.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auto-quiz")
@RequiredArgsConstructor
public class AutoQuizCoreController {

    private final AutoQuizCoreApi autoQuizCoreApi;

    @GetMapping("/task/{task-id}/quiz-pack")
    public ResponseEntity<CommonResponse<QueryAutoQuizPackResponse>> queryAutoQuizPack(
            AccessUser accessUser,
            @PathVariable("task-id") long taskId

    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "오토 퀴즈팩 조회 성공",
                        autoQuizCoreApi.queryAutoQuizPack(accessUser.getId(), taskId).getData()
                )
        );
    }

    @PutMapping("/auto-quiz-pack")
    public ResponseEntity<CommonResponse<QueryAutoQuizPackResponse>> commandUpdateAutoQuizPack(
            AccessUser accessUser,
            @RequestBody CommandAutoQuizPackUpdateRequest request
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "오토 퀴즈팩 저장 성공",
                        autoQuizCoreApi.commandUpdateAutoQuizPack(accessUser.getId(), request).getData()
                )
        );
    }

    @PostMapping("/auto-quiz-pack/{auto-quiz-pack-id}/quiz-pack")
    public ResponseEntity<CommonResponse<Long>> commandPublishAutoQuizPack(
            AccessUser accessUser,
            @PathVariable("auto-quiz-pack-id") long autoQuizPackId
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "오토 퀴즈팩 발행 성공",
                        autoQuizCoreApi.commandAutoQuizPack(accessUser.getId(), autoQuizPackId).getData()
                )
        );
    }


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
