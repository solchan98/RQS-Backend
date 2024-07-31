package org.example.quizbox.game.domain;

import java.util.HashMap;
import java.util.Map;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.Quiz;

public class SubmittedAnswers {

    private final Map<Quiz, SubmitAnswer> submitAnswers = new HashMap<>();

    public void submitAnswers(SubmitAnswer submitAnswer) {
        if (submitAnswers.containsKey(submitAnswer.quiz())) {
            throw new BusinessException(ExceptionConstants.QG5);
        }

        this.submitAnswers.put(submitAnswer.quiz(), submitAnswer);
    }

}
