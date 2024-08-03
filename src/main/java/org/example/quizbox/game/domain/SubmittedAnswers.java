package org.example.quizbox.game.domain;

import java.util.HashMap;
import java.util.Map;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

public class SubmittedAnswers {

    private final Map<Long, SubmitAnswer> submitAnswers = new HashMap<>();

    public void submitAnswers(SubmitAnswer submitAnswer) {
        if (submitAnswers.containsKey(submitAnswer.quizId())) {
            throw new BusinessException(ExceptionConstants.QG5);
        }

        this.submitAnswers.put(submitAnswer.quizId(), submitAnswer);
    }

    public int submittedQuizSize() {
        return submitAnswers.keySet().size();
    }

}
