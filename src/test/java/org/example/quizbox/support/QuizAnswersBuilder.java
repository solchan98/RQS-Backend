package org.example.quizbox.support;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.quizbox.quiz.domain2.Answer;
import org.example.quizbox.quiz.domain2.QuizAnswers;

public class QuizAnswersBuilder {

    private Set<Answer> values = new HashSet<>();

    public static QuizAnswersBuilder quizAnswersBuilder() {
        return new QuizAnswersBuilder();
    }

    public QuizAnswersBuilder answers(Answer... correctAnswers) {
        values.addAll(Arrays.asList(correctAnswers));
        return this;
    }

    public QuizAnswers build() {
        return new QuizAnswers(values);
    }

}
