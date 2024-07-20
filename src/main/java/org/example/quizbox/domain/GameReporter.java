package org.example.quizbox.domain;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameReporter {

    public GameReport report(GameQuizzes gameQuizzes, Map<Long, Answers> selectedAnswers) {
        Set<QuizMatch> quizMatches = gameQuizzes.values().stream()
                .map(quiz -> QuizMatch.of(quiz, selectedAnswers.getOrDefault(quiz.getId(), new Answers(Set.of()))))
                .collect(Collectors.toSet());

        return new GameReport(gameQuizzes.size(), quizMatches);
    }
}
