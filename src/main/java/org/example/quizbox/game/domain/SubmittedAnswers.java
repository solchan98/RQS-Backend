package org.example.quizbox.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SubmittedAnswers {

    private final Map<Long, SubmitAnswer> submitAnswers = new HashMap<>();

    private LocalDateTime lastSubmittedAt;

    public void submitAnswers(long quizId, SubmitAnswer submitAnswer) {
        if (submitAnswers.containsKey(quizId)) {
            throw new BusinessException(ExceptionConstants.QG5);
        }

        this.submitAnswers.put(quizId, submitAnswer);
        this.lastSubmittedAt = LocalDateTime.now();
    }

    public int submittedQuizSize() {
        return submitAnswers.size();
    }

    public LocalDateTime lastSubmittedAt() {
        return lastSubmittedAt;
    }

}
