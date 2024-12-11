package org.example.quizbox.game.presentation;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.application.StartQuiz;
import org.example.quizbox.game.domain.QuizPickStrategy;

public record StartQuizRequest(
        String quizPickStrategy,
        long quizPackId
) {

    public StartQuiz toStartQuiz(long memberId) {
        if (!QuizPickStrategy.containsByType(quizPickStrategy)) {
            throw new BusinessException(ExceptionConstants.QG10);
        }

        return new StartQuiz(QuizPickStrategy.valueOf(quizPickStrategy), quizPackId, memberId);
    }

}
