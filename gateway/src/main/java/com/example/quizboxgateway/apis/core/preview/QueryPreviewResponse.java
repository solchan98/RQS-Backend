package com.example.quizboxgateway.apis.core.preview;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QueryPreviewResponse {

    private Long id;
    private Long taskId;
    private Long userId;
    private String title;
    private String description;
    private List<String> keywords;
    private List<PreviewQuiz> quizzes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    record PreviewQuiz(
            Long id,
            String content,
            String description,
            List<PreviewOption> options
    ) {
    }


    record PreviewOption(
            Long id,
            String content,
            boolean correct
    ) {

    }

}
