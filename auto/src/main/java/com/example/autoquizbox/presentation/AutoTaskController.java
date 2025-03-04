package com.example.autoquizbox.presentation;

import com.example.autoquizbox.application.AutoTaskAddService;
import com.example.autoquizbox.application.AutoTaskReadAllService;
import com.example.autoquizbox.common.CommonResponse;
import com.example.autoquizbox.domain.AutoTask;
import com.example.autoquizbox.domain.AutoTaskRequest;
import com.example.autoquizbox.domain.vo.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("auto-task")
@RequiredArgsConstructor
public class AutoTaskController {

    private final AutoTaskReadAllService autoTaskReadAllService;

    private final AutoTaskAddService autoTaskAddService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<AutoTask>>> readAllAutoTasks(
            @RequestHeader("X-USER-ID") long userId,
            @RequestParam(value = "statuses", required = false) Set<TaskStatus> statuses
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 자동 생성 작업 조회 성공",
                        autoTaskReadAllService.query(userId, statuses)
                )
        );
    }

    @PostMapping
    public ResponseEntity<CommonResponse<AutoTask>> addAutoTask(
            @RequestHeader("X-USER-ID") long userId,
            @RequestBody AutoTaskRequest autoTaskRequest
    ) {
        return ResponseEntity.ok(new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 자동 생성 작업 추가 성공",
                        autoTaskAddService.command(userId, autoTaskRequest)
                )
        );
    }
}
