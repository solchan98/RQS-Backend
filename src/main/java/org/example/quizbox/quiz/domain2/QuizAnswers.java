package org.example.quizbox.quiz.domain2;

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
    private Set<Answer> answers = new HashSet<>();

    public QuizAnswers(Set<Answer> answers) {
        if (answers == null || answers.size() < MIN_SIZE_OF_ANSWER) {
            throw new BusinessException(ExceptionConstants.QA1);
        }

        boolean containsCorrectAnswer = answers.stream().anyMatch(Answer::isCorrect);
        if (!containsCorrectAnswer) {
            throw new BusinessException(ExceptionConstants.QA2);
        }
        this.answers = answers;
    }

    public boolean isMatchedCorrectAnswers(Set<Long> submitAnswerIds) {
        return false;
    }

    public Set<Answer> answers() {
        return new HashSet<>(answers);
    }

    public boolean containsAll(Set<Long> submitAnswerIds) {
        Set<Long> quizAnswerIds = answers().stream()
                .map(Answer::getId)
                .collect(Collectors.toSet());

        return quizAnswerIds.containsAll(submitAnswerIds);
    }

    public Set<Answer> correctAnswers() {
        return null;
    }

    public Set<Answer> incorrectAnswers() {
        return null;
    }
}
