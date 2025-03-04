package com.example.autoquizbox.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AutoQuizPack {

    private String title;

    private List<AutoQuiz> quizzes;

    private List<String> keywords;

    public AutoQuizPack(String title, List<String> keywords) {
        this.title = title;
        this.keywords = keywords;
    }

    public void update(List<AutoQuiz> quizzes, List<String> keywords) {
        this.quizzes = quizzes;
        this.keywords = keywords;
    }
}
