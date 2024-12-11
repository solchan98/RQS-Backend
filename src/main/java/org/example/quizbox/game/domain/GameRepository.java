package org.example.quizbox.game.domain;

import java.util.Optional;

public interface GameRepository {

    Game save(Game game);

    Optional<Game> findById(GameId id);

}
