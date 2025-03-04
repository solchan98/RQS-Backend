package com.example.quizboxgateway.apis.autoquiz.task;

import lombok.Getter;

@Getter
public class CommandTaskConfirmResponse {
    private long taskId;
    private PreviewResponse previewResponse;
    private String taskStatus;
    private String description;

    record PreviewResponse(long id, long taskId, String title, String previewQuizPackType) {
    }
}
