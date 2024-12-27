package org.example.quizbox.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public record SubmitOption(Set<Long> optionIds) {

    public SubmitOption {
        if (CollectionUtils.isEmpty(optionIds)) {
            throw new BusinessException(ExceptionConstants.QG6);
        }
    }

    public int size() {
        return optionIds.size();
    }

    public boolean containsAll(Set<Long> ids) {
        return optionIds.containsAll(ids);
    }
}
