package org.example.quizbox.common.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {

    private String status;
    private String message;
    private T data;
}
