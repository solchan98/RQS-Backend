package com.example.quizboxgateway.apis.core.preview;

import lombok.Getter;

import java.util.List;

@Getter
public class QueryPreviewResponse {

    private Long id;
    private Long taskId;
    private Long userId;
    private String title;
    private List<String> keywords;
    private List<PreviewQuiz> quizzes;

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
