package org.example.quizbox.game.domain;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

// ID 기준 순차 피커
@Component("SEQUENCE_PICK")
public class SequentialGameQuizPicker implements GameQuizPicker {

    @Override
    public Optional<GameQuiz> pick(Collection<GameQuiz> gameQuizzes) {
        return gameQuizzes.stream().min((a, b) -> Math.toIntExact(a.getQuizId() - b.getQuizId()));
    }
}
