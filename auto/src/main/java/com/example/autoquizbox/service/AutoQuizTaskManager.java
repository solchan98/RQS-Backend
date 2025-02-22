package com.example.autoquizbox.service;

import com.example.autoquizbox.service.command.AddTaskRequest;

public interface AutoQuizTaskManager {

    void addTask(long taskId, long userId, AddTaskRequest addTaskRequest);

    void pause();

    void resume();
}
