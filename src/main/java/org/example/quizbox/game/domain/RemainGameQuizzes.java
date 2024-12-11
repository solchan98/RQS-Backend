package org.example.quizbox.game.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RemainGameQuizzes {

    private Set<GameQuiz> gameQuizzes = new HashSet<>();

    private GameQuizPicker gameQuizPicker;

    public RemainGameQuizzes(GameQuizPicker gameQuizPicker) {
        this.gameQuizPicker = gameQuizPicker;
    }

    public RemainGameQuizzes(Set<GameQuiz> gameQuizzes, GameQuizPicker gameQuizPicker) {
        this.gameQuizzes = new HashSet<>(gameQuizzes);
        this.gameQuizPicker = gameQuizPicker;
    }

    public Optional<GameQuiz> pick() {
        Optional<GameQuiz> pick = gameQuizPicker.pick(gameQuizzes);
        if (pick.isEmpty()) {
            return Optional.empty();
        }

        pick.ifPresent(gameQuiz -> this.gameQuizzes.remove(gameQuiz));

        return pick;
    }

    public int size() {
        return gameQuizzes.size();
    }
}
