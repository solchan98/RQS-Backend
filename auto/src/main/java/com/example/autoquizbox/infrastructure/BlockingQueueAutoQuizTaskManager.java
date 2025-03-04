package com.example.autoquizbox.infrastructure;

import com.example.autoquizbox.common.ServiceUnavailableException;
import com.example.autoquizbox.domain.AutoTask;
import com.example.autoquizbox.domain.AutoTaskManager;
import com.example.autoquizbox.domain.AutoTaskRepository;
import com.example.autoquizbox.domain.CreateAutoQuizPack;
import com.example.autoquizbox.domain.vo.TaskStatus;
import com.example.autoquizbox.infrastructure.core.CoreApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class BlockingQueueAutoQuizTaskManager implements AutoTaskManager {

    private final PausableThreadPoolExecutor executorService;

    private final CreateAutoQuizPack createAutoQuizPack;

    private final CoreApi coreApi;

    private final AutoTaskRepository autoTaskRepository;

    @Override
    public void addTask(AutoTask autoTask) {
        if (executorService.isPaused()) {
            throw new ServiceUnavailableException("퀴즈 생성 불가 상태");
        }

        executorService.execute(() -> createAutoQuizPack.create(autoTask, getCallback()));
    }

    private Consumer<AutoTask> getCallback() {
        return (autoTask) -> {
            if (TaskStatus.PENDING_REVIEW == autoTask.getTaskStatus()) {
                pause();
            }

            autoTaskRepository.save(autoTask);
        };
    }

    @Override
    public void pause() {
        executorService.pause();
        System.out.println("⏸️ 큐에서 작업 꺼내는 것이 일시 정지됨.");
    }

    @Override
    public void resume() {
        executorService.resume();
        System.out.println("▶ 큐에서 작업 꺼내는 것이 재개됨.");
    }
}
