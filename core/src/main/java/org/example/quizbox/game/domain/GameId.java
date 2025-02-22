package org.example.quizbox.game.domain;

import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;

import java.util.UUID;

public record GameId(UUID id) {

    public static GameId from(String id) {
        try {
            return new GameId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ExceptionConstants.QG8);
        }
    }

    public static GameId create() {
        return new GameId(UUID.randomUUID());
    }

    public String value() {
        return id.toString();
    }

}
