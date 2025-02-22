package org.example.quizbox.game.domain;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Game save(Game game);

    Optional<Game> findBy(GameId id);

    List<Game> findAllBy(long memberId);

    void deleteBy(GameId id);
}
