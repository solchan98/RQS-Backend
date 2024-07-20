package org.example.quizbox.domain;

public record QuizContent(String value) {

    public static QuizContent blank() {
        return new QuizContent("");
    }
}
