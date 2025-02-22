package com.example.autoquizbox.infrastructure;

import com.example.autoquizbox.common.ServiceUnavailableException;
import com.example.autoquizbox.service.AutoQuizService;
import com.example.autoquizbox.service.AutoQuizTaskManager;
import com.example.autoquizbox.service.command.AddTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockingQueueAutoQuizTaskManager implements AutoQuizTaskManager {


    private final AutoQuizService autoQuizService;
    private final PausableThreadPoolExecutor executorService;

    @Override
    public void addTask(long taskId, long userId, AddTaskRequest addTaskRequest) {
        if (executorService.isPaused()) {
            throw new ServiceUnavailableException("퀴즈 생성 불가 상태");
        }
        executorService.execute(() -> autoQuizService.work(taskId, userId, addTaskRequest, this::pause));
    }

    public void pause() {
        executorService.pause();
        System.out.println("⏸️ 큐에서 작업 꺼내는 것이 일시 정지됨.");
    }

    public void resume() {
        executorService.resume();
        System.out.println("▶ 큐에서 작업 꺼내는 것이 재개됨.");
    }
}
