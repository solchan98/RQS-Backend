package org.example.quizbox.game.infrastructure;

import static org.example.quizbox.game.infrastructure.InMemoryStorage.quizGameStore;

import java.util.Optional;
import org.example.quizbox.game.domain.QuizGame;
import org.example.quizbox.game.domain.QuizGameId;
import org.example.quizbox.game.domain.QuizGameRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryQuizGameRepository implements QuizGameRepository {

    @Override
    public QuizGame save(QuizGame quizGame) {
        quizGameStore.put(quizGame.id(), quizGame);
        return quizGame;
    }

    @Override
    public Optional<QuizGame> findById(QuizGameId id) {
        if (!quizGameStore.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(quizGameStore.get(id));
    }
}
