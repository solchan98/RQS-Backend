package org.example.quizbox.infrastructure;

import static org.example.quizbox.infrastructure.InMemoryStorage.players;

import java.util.Optional;
import org.example.quizbox.domain.Player;
import org.example.quizbox.domain.PlayerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPlayerRepository implements PlayerRepository {

    @Override
    public Optional<Player> findById(long id) {
        if (!players.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(players.get(id));
    }
}
