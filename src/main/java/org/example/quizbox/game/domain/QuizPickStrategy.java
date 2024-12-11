package org.example.quizbox.game.domain;


import java.util.Arrays;
import java.util.stream.Collectors;

public enum QuizPickStrategy {
    SEQUENCE_PICK("SEQUENCE_PICK"),
    RANDOM_PICK("RANDOM_PICK");

    final String type;

    QuizPickStrategy(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    public static boolean containsByType(String type) {
        return Arrays.stream(values()).map(QuizPickStrategy::type).collect(Collectors.toSet()).contains(type);
    }
}
