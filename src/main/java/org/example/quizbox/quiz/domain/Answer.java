package org.example.quizbox.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Answer {

    private Long id;

    private String content;

    public Answer(String content) {
        this.content = content;
    }
}
