package org.example.quizbox.domain;

import java.util.Optional;

public interface GameRepository {

    Game save(Game game);

    Optional<Game> findById(String id);
}
