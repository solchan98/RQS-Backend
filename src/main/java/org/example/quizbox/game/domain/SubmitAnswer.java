package org.example.quizbox.game.domain;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG4;
import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG6;

import java.util.Set;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.quiz.domain.Quiz;
import org.springframework.util.CollectionUtils;

public record SubmitAnswer(Quiz quiz, Set<Long> answers) {

    public SubmitAnswer {
        if (CollectionUtils.isEmpty(answers)) {
            throw new BusinessException(QG6);
        }

        boolean containsAll = quiz.containsAllAnswers(answers);
        if (!containsAll) {
            throw new BusinessException(QG4);
        }
    }

}
