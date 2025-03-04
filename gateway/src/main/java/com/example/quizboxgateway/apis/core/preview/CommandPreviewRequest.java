package com.example.quizboxgateway.apis.core.preview;

import lombok.Getter;

import java.util.List;

@Getter
public class CommandPreviewRequest {
    private Long id;
    private Long taskId;
    private Long userId;
    private String title;
    private List<String> keywords;
    private List<QueryPreviewResponse.PreviewQuiz> quizzes;
}
