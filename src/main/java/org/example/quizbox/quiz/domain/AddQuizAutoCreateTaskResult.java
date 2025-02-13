package org.example.quizbox.quiz.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class AddQuizAutoCreateTaskResult {

    private Data data;

    @JsonCreator
    public AddQuizAutoCreateTaskResult(
            @JsonProperty("data") Data data
    ) {
        this.data = data;
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class Data {
        private Long id;
        private Long userId;
        private String taskStatus;
        private String taskResult;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

    }

}

