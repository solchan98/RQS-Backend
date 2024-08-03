package org.example.quizbox.game.infrastructure;

import java.util.HashMap;
import java.util.Map;
import org.example.quizbox.game.domain.QuizGame;
import org.example.quizbox.game.domain.QuizGameId;

public final class InMemoryStorage {

    private InMemoryStorage() {
    }

    public static final Map<QuizGameId, QuizGame> quizGameStore = new HashMap<>();

}
