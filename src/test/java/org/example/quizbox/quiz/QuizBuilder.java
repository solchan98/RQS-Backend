package org.example.quizbox.quiz;

import java.util.Set;
import org.example.quizbox.quiz.domain.Answer;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizAnswers;
import org.example.quizbox.quiz.domain.QuizContent;

public class QuizBuilder {

    private static long DEFAULT_KEY = 0;

    private Long id;
    private QuizContent content = new QuizContent("default quiz content");
    private QuizAnswers quizAnswers = new QuizAnswers(Set.of(new Answer("A"), new Answer("B")), Set.of());

    public static QuizBuilder quizBuilder() {
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

    public QuizBuilder quizAnswers(Set<Answer> correctAnswers, Set<Answer> incorrectAnswers) {
        this.quizAnswers = new QuizAnswers(correctAnswers, incorrectAnswers);
        return this;
    }

    public QuizBuilder quizAnswers(QuizAnswers quizAnswers) {
        this.quizAnswers = quizAnswers;
        return this;
    }


    public Quiz build() {
        return new Quiz(id, content, quizAnswers);
    }
}
