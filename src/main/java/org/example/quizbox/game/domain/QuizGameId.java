package org.example.quizbox.game.domain;

import java.util.UUID;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

public record QuizGameId(UUID id) {

    public static QuizGameId from(String id) {
        try {
            return new QuizGameId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ExceptionConstants.QG8);
        }
    }

    public static QuizGameId create() {
        return new QuizGameId(UUID.randomUUID());
    }

    public String value() {
        return id.toString();
    }

}
