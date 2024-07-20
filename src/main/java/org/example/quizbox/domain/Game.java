package org.example.quizbox.domain;

import static org.example.quizbox.domain.exception.ExceptionConstants.GM1;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM2;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM3;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM4;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM6;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.example.quizbox.domain.exception.BusinessException;

@Getter
public class Game {

    private String id;

    private final GameQuizzes gameQuizzes;

    private final Map<Long, Answers> selectedAnswers = new HashMap<>();

    private final GameReporter gameReporter;

    private final Set<Player> players;

    public Game(GameQuizzes gameQuizzes, Set<Player> players, GameReporter gameReporter) {
        this.id = UUID.randomUUID().toString();
        if (gameQuizzes == null || gameQuizzes.isEmpty()) {
            throw new BusinessException(GM1.code());
        }
        this.gameQuizzes = gameQuizzes;
        this.players = players;
        this.gameReporter = gameReporter;
    }

    public int quizSize() {
        return gameQuizzes.size();
    }

    public Optional<Quiz> nextQuiz(Player player) {
        validatePlayer(player);
        if (!gameQuizzes.existsNextQuiz()) {
            return Optional.empty();
        }

        return gameQuizzes.nextQuiz();
    }

    public void submitAnswer(long quizId, Answers answers, Player player) {
        validatePlayer(player);
        if (!gameQuizzes.isSubmittableAnswers(quizId)) {
            throw new BusinessException(GM3.code());
        }

        if (isAnswerSubmitted(quizId)) {
            throw new BusinessException(GM2.code());
        }

        selectedAnswers.put(quizId, answers);
    }

    public boolean isAnswerSubmitted(long quizId) {
        return selectedAnswers.containsKey(quizId);
    }

    public GameReport report(Player player) {
        validatePlayer(player);
        if (gameQuizzes.existsNextQuiz()) {
            throw new BusinessException(GM4.code());
        }
        return gameReporter.report(gameQuizzes, selectedAnswers);
    }

    private void validatePlayer(Player player) {
        if (!isPlayer(player)) {
            throw new BusinessException(GM6.code());
        }
    }

    public boolean isPlayer(Player player) {
        return players.contains(player);
    }
}
