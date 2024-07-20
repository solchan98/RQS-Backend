package org.example.quizbox.domain;

import java.util.Optional;

public interface PlayerRepository {
    Optional<Player> findById(long id);

}
