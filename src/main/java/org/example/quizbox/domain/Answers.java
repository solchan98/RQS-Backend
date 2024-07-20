package org.example.quizbox.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record Answers(Set<Answer> values) {

    public static Answers from(Answer... values) {
        return new Answers(Set.of(values));
    }

    public static Answers from(Answers... answers) {
        return new Answers(Arrays.stream(answers)
                .map(Answers::values)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()));
    }

    public boolean isMatchedCorrectAnswers(Answers submitAnswers) {
        Answers correctAnswers = new Answers(values.stream().filter(Answer::getCorrect).collect(Collectors.toSet()));
        return correctAnswers.equals(submitAnswers);
    }
}
