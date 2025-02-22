package com.example.autoquizbox.service.command;

import com.example.autoquizbox.entities.*;
import com.example.autoquizbox.service.AutoQuizTaskManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutoQuizTaskAddCommandService {

    private final AutoQuizTaskManager autoQuizTaskManager;

    private final AutoQuizTaskHistoryRepository autoQuizTaskHistoryRepository;
    private final AutoQuizPackRepository autoQuizPackRepository;

    @Transactional
    public AutoQuizTaskHistory command(long userId, AddTaskRequest addTaskRequest) {
        AutoQuizTaskHistory autoQuizTaskHistory = AutoQuizTaskHistory.startConstructor(userId);
        autoQuizTaskHistoryRepository.save(autoQuizTaskHistory);

        autoQuizTaskManager.addTask(autoQuizTaskHistory.getId(), userId, addTaskRequest);
        autoQuizTaskHistory.update(TaskStatus.ADDED, TaskResult.WAITING, "작업 추가");

        saveSimpleAutoQuizPack(addTaskRequest, userId, autoQuizTaskHistory);

        return autoQuizTaskHistoryRepository.save(autoQuizTaskHistory);
    }

    private void saveSimpleAutoQuizPack(AddTaskRequest addTaskRequest, long userId, AutoQuizTaskHistory autoQuizTaskHistory) {
        AutoQuizPack autoQuizPack = new AutoQuizPack(autoQuizTaskHistory.getId(), userId, addTaskRequest.getQuizPackTitle());
        autoQuizPackRepository.save(autoQuizPack);
    }

    public void pause() {
        autoQuizTaskManager.pause();
    }

    public void resume() {
        autoQuizTaskManager.resume();
    }
}
