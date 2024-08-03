package org.example.quizbox.game.application;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.example.quizbox.game.domain.GameQuizPicker;
import org.example.quizbox.game.domain.SequentialGameQuizPicker;

public enum QuizPickStrategy {
    SEQUENCE_PICK("SEQUENCE", new SequentialGameQuizPicker()),
    RANDOM_PICK("RANDOM", new SequentialGameQuizPicker());

    final String type;

    final GameQuizPicker gameQuizPicker;

    QuizPickStrategy(String type, GameQuizPicker gameQuizPicker) {
        this.type = type;
        this.gameQuizPicker = gameQuizPicker;
    }

    public String type() {
        return type;
    }

    public static boolean containsByType(String type) {
        return Arrays.stream(values()).map(QuizPickStrategy::type).collect(Collectors.toSet()).contains(type);
    }
}
