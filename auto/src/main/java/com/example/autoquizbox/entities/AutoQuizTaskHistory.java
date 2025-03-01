package com.example.autoquizbox.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AutoQuizTaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_result", nullable = false)
    private TaskResult taskResult;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public static AutoQuizTaskHistory startConstructor(long userId) {
        return new AutoQuizTaskHistory(null, userId, TaskStatus.ADDED, TaskResult.WAITING, null,
                LocalDateTime.now(), LocalDateTime.now());
    }

    public void update(
            TaskStatus taskStatus,
            TaskResult taskResult,
            String description
    ) {
        this.taskStatus = taskStatus;
        this.taskResult = taskResult;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void review() {
        if (this.taskStatus == TaskStatus.PENDING_REVIEW) {
            this.taskStatus = TaskStatus.FINISH;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void publish() {
        if (this.taskStatus == TaskStatus.WAITING_TO_BE_PUBLISHED) {
            this.taskStatus = TaskStatus.FINISH;
        }
        this.updatedAt = LocalDateTime.now();
    }

}
