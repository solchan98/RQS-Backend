package org.example.quizbox.quiz;

import org.example.quizbox.quiz.domain.*;

import java.util.Objects;
import java.util.Set;


public class QuizBuilder {

    private static long DEFAULT_KEY = 0;

    private Long id;
    private QuizPackMember creator;
    private QuizContent content = new QuizContent("default quiz content");
    private Options options = new Options(Set.of(Option.falseOption("A"), Option.trueOption("B")));

    public static QuizBuilder quizBuilder() {
        QuizBuilder quizBuilder = new QuizBuilder();
        quizBuilder.id = --DEFAULT_KEY;
        return quizBuilder;
    }

    public QuizBuilder id(long id) {
        this.id = id;
        return this;
    }

    public QuizBuilder creator(QuizPackMember creator) {
        this.creator = creator;
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

    public QuizBuilder options(Option... options) {
        this.options = new Options(Set.of(options));
        return this;
    }

    public Quiz build() {
        if (Objects.isNull(creator)) {
            creator = new QuizPackMember(1L, QuizPackMemberRole.ADMIN);
        }

        return new Quiz(id, content, creator, options);
    }
}
