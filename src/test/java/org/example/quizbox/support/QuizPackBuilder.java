package org.example.quizbox.support;

import java.util.ArrayList;
import java.util.Collection;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;

public class QuizPackBuilder {

    private Long id;
    private String title = "default title";
    private Collection<Quiz> quizzes = new ArrayList<>();

    public static QuizPackBuilder quizPackBuilder() {
        return new QuizPackBuilder();
    }

    public QuizPackBuilder id(long id) {
        this.id = id;
        return this;
    }

    ;

    public QuizPackBuilder title(String title) {
        this.title = title;
        return this;
    }

    ;

    public QuizPackBuilder quizzes(Collection<Quiz> quizzes) {
        this.quizzes = new ArrayList<>(quizzes);
        return this;
    }

    public QuizPack build() {
        return new QuizPack(id, title, quizzes);
    }
}
