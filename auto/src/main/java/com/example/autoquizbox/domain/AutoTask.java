package com.example.autoquizbox.domain;

import com.example.autoquizbox.domain.vo.AutoQuiz;
import com.example.autoquizbox.domain.vo.TaskStatus;
import com.example.autoquizbox.infrastructure.AutoTaskRequestConverter;
import com.example.autoquizbox.infrastructure.AutoTaskResultDetailConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AutoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "request", columnDefinition = "json")
    @Convert(converter = AutoTaskRequestConverter.class)
    private AutoTaskRequest request;

    @Column(name = "result", columnDefinition = "json")
    @Convert(converter = AutoTaskResultDetailConverter.class)
    private AutoTaskDetailResult detailResult;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static AutoTask of(long userId, AutoTaskRequest request) {
        return new AutoTask(
                null,
                userId,
                request,
                AutoTaskDetailResult.from(request.getQuizPackTitle()),
                TaskStatus.ADDED,
                LocalDateTime.now(),
                null
        );
    }

    public void updateDetail(TaskStatus taskStatus, String taskDescription) {
        this.taskStatus = taskStatus;
        this.updatedAt = LocalDateTime.now();
        this.detailResult.update(List.of(), "", List.of(), taskDescription);
    }

    public void updateDetail(
            List<AutoQuiz> quizzes,
            String quizPackDescription,
            List<String> keywords,
            TaskStatus taskStatus,
            String taskDescription
    ) {
        this.taskStatus = taskStatus;
        this.updatedAt = LocalDateTime.now();
        this.detailResult.update(quizzes, quizPackDescription, keywords, taskDescription);
    }

    public void updateState(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
