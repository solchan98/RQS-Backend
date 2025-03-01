package com.example.autoquizbox.infrastructure;

import com.example.autoquizbox.entities.AutoQuiz;
import lombok.Getter;

import java.util.List;

@Getter
public class GeneratedQuizResponse {
    private List<String> keywords;
    private List<AutoQuiz> quizzes;
}
