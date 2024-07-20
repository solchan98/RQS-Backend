package org.example.quizbox.domain;

import java.util.Set;

public class QuizBuilder {

    private static long DEFAULT_KEY = 0;

    private Long id;
    private QuizContent content = new QuizContent("default quiz content");
    private Answers answers = new Answers(Set.of());

    public static QuizBuilder builder() {
        QuizBuilder quizBuilder = new QuizBuilder();
        quizBuilder.id = --DEFAULT_KEY;
        return quizBuilder;
    }

    public QuizBuilder id(long id) {
        this.id = id;
        return this;
    }

    public QuizBuilder content(String content) {
        this.content = new QuizContent(content);
        return this;
    }

    public QuizBuilder content(QuizContent content) {
        this.content = content;
        return this;
    }

    public QuizBuilder answers(Answer... answers) {
        this.answers = Answers.from(answers);
        return this;
    }

    public QuizBuilder answers(Answers answers) {
        this.answers = answers;
        return this;
    }


    public Quiz build() {
        return new Quiz(id, content, answers);

    }
}
