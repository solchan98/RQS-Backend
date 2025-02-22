package com.example.autoquizbox.presentation;

import com.example.autoquizbox.entities.AutoQuizTaskHistory;
import com.example.autoquizbox.entities.TaskResult;
import com.example.autoquizbox.entities.TaskStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AutoQuizTaskStatus {
    private String quizPackTitle;

    private long taskId;

    private TaskStatus taskStatus;

    private TaskResult taskResult;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public AutoQuizTaskStatus(String quizPackTitle, AutoQuizTaskHistory autoQuizTaskHistory) {
        this.quizPackTitle = quizPackTitle;
        this.taskId = autoQuizTaskHistory.getId();
        this.taskStatus = autoQuizTaskHistory.getTaskStatus();
        this.taskResult = autoQuizTaskHistory.getTaskResult();
        this.description = autoQuizTaskHistory.getDescription();
        this.createdAt = autoQuizTaskHistory.getCreatedAt();
        this.updatedAt = autoQuizTaskHistory.getUpdatedAt();
    }
}
