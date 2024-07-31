package org.example.quizbox.game.domain;

import java.util.Collection;
import java.util.Optional;
import org.example.quizbox.quiz.domain.Quiz;

// ID 기준 순차 피커
public class SequentialGameQuizPicker implements GameQuizPicker {

    @Override
    public Optional<Quiz> pick(Collection<Quiz> gameQuizzes) {
        return gameQuizzes.stream().min((a, b) -> Math.toIntExact(a.getId() - b.getId()));
    }
}
