package org.example.quizbox.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public record SubmitAnswer(Set<Long> answersIds) {

    public SubmitAnswer {
        if (CollectionUtils.isEmpty(answersIds)) {
            throw new BusinessException(ExceptionConstants.QG6);
        }
    }
}
