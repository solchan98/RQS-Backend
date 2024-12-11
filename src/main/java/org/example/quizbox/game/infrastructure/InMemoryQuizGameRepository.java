package org.example.quizbox.game.infrastructure;

import org.example.quizbox.game.domain.Game;
import org.example.quizbox.game.domain.GameId;
import org.example.quizbox.game.domain.GameRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.example.quizbox.game.infrastructure.InMemoryStorage.quizGameStore;

@Repository
public class InMemoryQuizGameRepository implements GameRepository {

    @Override
    public Game save(Game game) {
        quizGameStore.put(game.getId(), game);
        return game;
    }

    @Override
    public Optional<Game> findById(GameId id) {
        if (!quizGameStore.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(quizGameStore.get(id));
    }
}
