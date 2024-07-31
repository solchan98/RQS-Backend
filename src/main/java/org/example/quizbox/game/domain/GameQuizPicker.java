package org.example.quizbox.game.domain;

import java.util.Collection;
import java.util.Optional;
import org.example.quizbox.quiz.domain.Quiz;

public interface GameQuizPicker {

    Optional<Quiz> pick(Collection<Quiz> quizzes);
}
