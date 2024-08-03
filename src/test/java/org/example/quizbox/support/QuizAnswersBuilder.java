package org.example.quizbox.support;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.quizbox.quiz.domain.Answer;
import org.example.quizbox.quiz.domain.QuizAnswers;

public class QuizAnswersBuilder {

    private Set<Answer> correctAnswers = new HashSet<>(
            Set.of(new Answer("B"))
    );

    private Set<Answer> incorrectAnswers = new HashSet<>(
            Set.of(new Answer("B"))
    );

    public static QuizAnswersBuilder quizAnswersBuilder() {
        return new QuizAnswersBuilder();
    }

    public QuizAnswersBuilder correctAnswers(Answer... correctAnswers) {
        this.correctAnswers = Arrays.stream(correctAnswers).collect(Collectors.toSet());
        return this;
    }

    public QuizAnswersBuilder incorrectAnswers(Answer... correctAnswers) {
        this.incorrectAnswers = Arrays.stream(correctAnswers).collect(Collectors.toSet());
        return this;
    }

    public QuizAnswers build() {
        return new QuizAnswers(correctAnswers, incorrectAnswers);
    }

}
