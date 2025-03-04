package com.example.autoquizbox.application;

import com.example.autoquizbox.common.BusinessException;
import com.example.autoquizbox.domain.AutoTask;
import com.example.autoquizbox.domain.AutoTaskRepository;
import com.example.autoquizbox.domain.vo.TaskStatus;
import com.example.autoquizbox.infrastructure.core.CoreApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AutoTaskConfirmService {

    private final CoreApi coreApi;

    private final AutoTaskRepository autoTaskRepository;

    public AutoTaskConfirmResponse command(long userId, long taskId) {
        AutoTask autoTask = autoTaskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new BusinessException(404, "작업이 존재하지 않습니다."));
        List<TaskStatus> targetStatuses = List.of(TaskStatus.PENDING_REVIEW, TaskStatus.WAITING_TO_BE_PUBLISHED);

        if (!targetStatuses.contains(autoTask.getTaskStatus())) {
            throw new BusinessException(400, "확인 대상 작업이 아닙니다.");
        }

        if (TaskStatus.PENDING_REVIEW == autoTask.getTaskStatus()) {
            return commandWhenPendingReview(autoTask);
        }

        return commandWhenWaitingToBePublish(autoTask);
    }

    private AutoTaskConfirmResponse commandWhenPendingReview(AutoTask autoTask) {
        autoTask.updateState(TaskStatus.FAIL);
        return AutoTaskConfirmResponse.fail(autoTask.getId());
    }

    private AutoTaskConfirmResponse commandWhenWaitingToBePublish(AutoTask autoTask) {
        autoTask.updateState(TaskStatus.SUCCESS);
        PreviewResponse previewResponse = coreApi.commandPreviewQuizPack(
                autoTask.getUserId(),
                autoTask.getId(),
                autoTask.getDetailResult().getAutoQuizPack()
        ).getData();

        return AutoTaskConfirmResponse.success(autoTask.getId(), previewResponse);
    }
}
