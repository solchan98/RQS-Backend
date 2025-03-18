package com.example.autoquizbox.domain;

import com.example.autoquizbox.domain.vo.AutoQuiz;
import com.example.autoquizbox.domain.vo.AutoQuizPack;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AutoTaskDetailResult {

    private AutoQuizPack autoQuizPack;

    private String description;

    public static AutoTaskDetailResult from(String quizPackTitle) {
        return new AutoTaskDetailResult(
                new AutoQuizPack(quizPackTitle, "", List.of()),
                ""
        );
    }

    public void update(List<AutoQuiz> quizzes, String quizPackDescription, List<String> keywords, String taskDescription) {
        autoQuizPack.update(quizzes, quizPackDescription, keywords);
        this.description = description;
    }

}
