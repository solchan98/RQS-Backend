package org.example.quizbox.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

import java.util.HashMap;
import java.util.Map;

public class SubmittedAnswers {

    private final Map<Long, SubmitAnswer> submitAnswers = new HashMap<>();

    public void submitAnswers(long quizId, SubmitAnswer submitAnswer) {
        if (submitAnswers.containsKey(quizId)) {
            throw new BusinessException(ExceptionConstants.QG5);
        }

        this.submitAnswers.put(quizId, submitAnswer);
    }

    public int submittedQuizSize() {
        return submitAnswers.keySet().size();
    }

}
