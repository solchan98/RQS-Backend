package com.example.autoquizbox.application;

import com.example.autoquizbox.domain.AutoTaskManager;
import com.example.autoquizbox.domain.AutoTask;
import com.example.autoquizbox.domain.AutoTaskRepository;
import com.example.autoquizbox.domain.AutoTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AutoTaskAddService {

    private final AutoTaskRepository autoTaskRepository;

    private final AutoTaskManager autoTaskManager;

    /**
     * 자동 퀴즈 생성 추가
     *
     * @return 추가된 작업 id
     */
    public AutoTask command(long userId, AutoTaskRequest autoTaskRequest) {
        AutoTask autoTask = AutoTask.of(userId, autoTaskRequest);

        autoTaskRepository.save(autoTask);
        autoTaskManager.addTask(autoTask);

        return autoTask;
    }
}
