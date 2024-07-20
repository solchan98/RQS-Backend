package org.example.quizbox.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameBuilder {

    private GameQuizzes gameQuizzes = SequentialGameQuizzes.from();

    private Map<Long, Set<String>> selectedAnswers = new HashMap<>();

    private GameReporter gameReporter = new GameReporter();

    private Set<Player> players = new HashSet<>();

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public GameBuilder quizzes(GameQuizzes gameQuizzes) {
        this.gameQuizzes = gameQuizzes;
        return this;
    }

    public GameBuilder randomQuizzes() {
        List<Quiz> randomQuizzes = new Random().ints(5).mapToObj(idx ->
                        QuizBuilder.builder()
                                .content("quiz" + idx)
                                .answers(AnswerBuilder.builder()
                                        .correct(true)
                                        .content(String.valueOf(idx))
                                        .buildForQuiz())
                                .build()
                ).toList();
        this.gameQuizzes = SequentialGameQuizzes.from(randomQuizzes);
        return this;
    }

    public GameBuilder selectedAnswers(Map<Long, Set<String>> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
        return this;
    }

    public GameBuilder gameReporter(GameReporter gameReporter) {
        this.gameReporter = gameReporter;
        return this;
    }

    public GameBuilder players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Game build() {
        if (gameQuizzes == null || gameQuizzes.isEmpty()) {
            return new Game(SequentialGameQuizzes.from(), players, gameReporter);
        }

        return new Game(gameQuizzes, players, gameReporter);
    }
}
