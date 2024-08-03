package org.example.quizbox.game.domain;

import java.util.Optional;

public interface QuizGameRepository {

    QuizGame save(QuizGame quizGame);

    Optional<QuizGame> findById(QuizGameId id);

}
