package org.example.quizbox.game.domain;

import java.util.Collection;
import java.util.Optional;

public interface GameQuizPicker {

    Optional<GameQuiz> pick(Collection<GameQuiz> gameQuizzes);
}
