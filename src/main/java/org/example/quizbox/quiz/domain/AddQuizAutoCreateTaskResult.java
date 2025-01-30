package org.example.quizbox.quiz.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddQuizAutoCreateTaskResult {

    private long requestUserId;

    private String jobId;

    private AddTaskStatus status;

    private String message;

    // TODO: Queue Status

    public enum AddTaskStatus {
        SUCCESS, FAILED
    }
}
