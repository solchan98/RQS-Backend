package org.example.quizbox.infrastructure;

import static org.example.quizbox.infrastructure.InMemoryStorage.games;

import java.util.Optional;
import org.example.quizbox.domain.Game;
import org.example.quizbox.domain.GameRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryGameRepository implements GameRepository {

    @Override
    public Game save(Game game) {
        games.put(game.getId(), game);
        return game;
    }

    @Override
    public Optional<Game> findById(String id) {
        if (!games.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(games.get(id));
    }
}
