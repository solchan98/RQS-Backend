package org.example.quizbox.quiz.domain;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.springframework.util.CollectionUtils;

public record QuizAnswers(Set<Answer> correctAnswers, Set<Answer> incorrectAnswers) {

    private static final long MIN_SIZE_OF_ANSWER = 2;

    public QuizAnswers {
        if (CollectionUtils.isEmpty(correctAnswers)) {
            throw new BusinessException(ExceptionConstants.QA2);
        }

        int answersSize = correctAnswers.size() + incorrectAnswers.size();
        if (answersSize < MIN_SIZE_OF_ANSWER) {
            throw new BusinessException(ExceptionConstants.QA1);
        }
    }

    public static QuizAnswers of(Set<Answer> correctAnswers, Set<Answer> incorrectAnswers) {
        return new QuizAnswers(correctAnswers, incorrectAnswers);
    }

    public boolean isMatchedCorrectAnswers(Set<Long> submitAnswerIds) {
        Set<Long> correctAnswerIds = correctAnswers.stream().map(Answer::getId).collect(Collectors.toSet());

        return correctAnswerIds.equals(submitAnswerIds);
    }

    public Set<Answer> answers() {
        return Stream.concat(correctAnswers.stream(), incorrectAnswers.stream()).collect(Collectors.toSet());
    }

    public boolean containsAll(Set<Long> submitAnswerIds) {
        Set<Long> quizAnswerIds = answers().stream()
                .map(Answer::getId)
                .collect(Collectors.toSet());

        return quizAnswerIds.containsAll(submitAnswerIds);
    }
}
