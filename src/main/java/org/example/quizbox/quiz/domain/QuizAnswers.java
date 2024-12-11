package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor
public class QuizAnswers {

    private static final long MIN_SIZE_OF_ANSWER = 2;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_id")
    private Set<Answer> values = new HashSet<>();

    public QuizAnswers(Set<Answer> values) {
        if (values == null || values.size() < MIN_SIZE_OF_ANSWER) {
            throw new BusinessException(ExceptionConstants.QA1);
        }

        boolean containsCorrectAnswer = values.stream().anyMatch(Answer::isCorrect);
        if (!containsCorrectAnswer) {
            throw new BusinessException(ExceptionConstants.QA2);
        }
        this.values = values;
    }

    public boolean isMatchedCorrectAnswers(Set<Long> submitAnswerIds) {
        Set<Long> collectOptionIds = correctAnswers().stream().map(Answer::getId).collect(Collectors.toSet());

        return collectOptionIds.containsAll(submitAnswerIds) && collectOptionIds.size() == submitAnswerIds.size();
    }

    public Set<Answer> answers() {
        return new HashSet<>(values);
    }

    public boolean containsAll(Set<Long> submitAnswerIds) {
        Set<Long> quizAnswerIds = answers().stream()
                .map(Answer::getId)
                .collect(Collectors.toSet());

        return quizAnswerIds.containsAll(submitAnswerIds);
    }

    public Set<Answer> correctAnswers() {
        return values.stream().filter(Answer::isCorrect).collect(Collectors.toSet());
    }

    public Set<Answer> incorrectAnswers() {
        return values.stream().filter(answer -> !answer.isCorrect()).collect(Collectors.toSet());
    }
}
