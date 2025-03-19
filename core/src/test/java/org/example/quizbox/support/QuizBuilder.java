package org.example.quizbox.support;

import org.example.quizbox.quiz.domain.*;

import java.util.Set;


public class QuizBuilder {

    private Long id;
    private QuizPackMember creator;
    private QuizContent content = new QuizContent("default quiz content");
    private Options options = new Options(Set.of(Option.falseOption("A"), Option.trueOption("B")));

    public static QuizBuilder quizBuilder() {
        return new QuizBuilder();
    }

    public QuizBuilder id(long id) {
        this.id = id;
        return this;
    }

    public QuizBuilder creator(QuizPackMember creator) {
        this.creator = creator;
        return this;
    }

    public QuizBuilder content(QuizContent content) {
        this.content = content;
        return this;
    }

    public QuizBuilder options(Option... options) {
        this.options = new Options(Set.of(options));
        return this;
    }

    public Quiz build(QuizPackMember creator) {
        return new Quiz(id, content, creator, options);
    }
}
