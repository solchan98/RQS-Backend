package org.example.quizbox.domain;

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
            throw BusinessException.atLeastOneQuizRequired();
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
            throw BusinessException.isNotAQuizCurrentlyInProgress();
        }

        if (isAnswerSubmitted(quizId)) {
            throw BusinessException.hasAlreadyBeenAnswered();
        }

        selectedAnswers.put(quizId, answers);
    }

    public boolean isAnswerSubmitted(long quizId) {
        return selectedAnswers.containsKey(quizId);
    }

    public GameReport report(Player player) {
        validatePlayer(player);
        if (gameQuizzes.existsNextQuiz()) {
            throw BusinessException.quizIsStillInProgress();
        }
        return gameReporter.report(gameQuizzes, selectedAnswers);
    }

    private void validatePlayer(Player player) {
        if (!isPlayer(player)) {
            throw BusinessException.notAGameParticipant();
        }
    }

    public boolean isPlayer(Player player) {
        return players.contains(player);
    }
}
