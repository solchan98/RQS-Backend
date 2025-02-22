package org.example.quizbox.game.infrastructure;

import org.example.quizbox.game.domain.Game;
import org.example.quizbox.game.domain.GameId;
import org.example.quizbox.game.domain.GameRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.quizbox.game.infrastructure.InMemoryStorage.quizGameStore;
import static org.example.quizbox.game.infrastructure.InMemoryStorage.quizGameStoreWithMemberIdKey;

@Repository
public class InMemoryQuizGameRepository implements GameRepository {

    @Override
    public Game save(Game game) {
        quizGameStore.put(game.getId(), game);

        Set<Game> games = quizGameStoreWithMemberIdKey.getOrDefault(game.getCreatorId(), Set.of());

        Set<Game> newGames = games.stream()
                .filter(prevGame -> !prevGame.getId().equals(game.getId()))
                .collect(Collectors.toSet());
        newGames.add(game);

        quizGameStoreWithMemberIdKey.put(game.getCreatorId(), newGames);
        return game;
    }

    @Override
    public Optional<Game> findBy(GameId id) {
        if (!quizGameStore.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(quizGameStore.get(id));
    }

    @Override
    public List<Game> findAllBy(long memberId) {
        return new ArrayList<>(quizGameStoreWithMemberIdKey.getOrDefault(memberId, Set.of()));
    }

    @Override
    public void deleteBy(GameId id) {
        Game game = quizGameStore.remove(id);

        Optional<Map.Entry<Long, Set<Game>>> find = quizGameStoreWithMemberIdKey.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(game))
                .findFirst();

        if (find.isPresent()) {
            Map.Entry<Long, Set<Game>> entry = find.get();
            entry.getValue().remove(game);
            quizGameStoreWithMemberIdKey.put(entry.getKey(), entry.getValue());
        }
    }
}
