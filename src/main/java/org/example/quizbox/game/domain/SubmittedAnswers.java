package org.example.quizbox.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SubmittedAnswers {

    private final Map<GameQuiz, SubmitAnswer> submitAnswers = new HashMap<>();

    private LocalDateTime lastSubmittedAt;

    public void submitAnswers(GameQuiz gameQuiz, SubmitAnswer submitAnswer) {
        validateDuplicateSubmission(gameQuiz);

        this.submitAnswers.put(gameQuiz, submitAnswer);
        this.lastSubmittedAt = LocalDateTime.now();
    }

    private void validateDuplicateSubmission(GameQuiz gameQuiz) {
        boolean contains = submitAnswers.keySet().stream().anyMatch(value -> value.equals(gameQuiz));
        if (contains) {
            throw new BusinessException(ExceptionConstants.QG5);
        }
    }

    public int size() {
        return submitAnswers.size();
    }

    public LocalDateTime lastSubmittedAt() {
        return lastSubmittedAt;
    }

}
