package org.example.quizbox.game.infrastructure;

import org.example.quizbox.game.domain.Game;
import org.example.quizbox.game.domain.GameId;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryStorage {

    private InMemoryStorage() {
    }

    public static final Map<GameId, Game> quizGameStore = new HashMap<>();

}
