package com.example.autoquizbox.application;

import com.example.autoquizbox.domain.vo.TaskStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.autoquizbox.domain.vo.TaskStatus.FAIL;
import static com.example.autoquizbox.domain.vo.TaskStatus.SUCCESS;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AutoTaskConfirmResponse {
    private long taskId;
    private PreviewResponse previewResponse;
    private TaskStatus taskStatus;
    private String description;

    public static AutoTaskConfirmResponse success(long taskId, PreviewResponse previewResponse) {
        return new AutoTaskConfirmResponse(taskId, previewResponse, SUCCESS, "프리뷰 퀴즈팩 생성 성공");
    }

    public static AutoTaskConfirmResponse fail(long taskId) {
        return new AutoTaskConfirmResponse(taskId, null, FAIL, "실패 작업 확인 성공");
    }
}
