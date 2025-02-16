package org.example.quizbox.game.domain;

import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SubmittedGameQuizzes {

    private final Map<GameQuiz, SubmitOption> submitGameQuizzes = new HashMap<>();

    private LocalDateTime lastSubmittedAt;

    public void submitOptions(GameQuiz gameQuiz, SubmitOption submitOption) {
        validateDuplicateSubmission(gameQuiz);

        this.submitGameQuizzes.put(gameQuiz, submitOption);
        this.lastSubmittedAt = LocalDateTime.now();
    }

    private void validateDuplicateSubmission(GameQuiz gameQuiz) {
        boolean contains = submitGameQuizzes.keySet().stream().anyMatch(value -> value.equals(gameQuiz));
        if (contains) {
            throw new BusinessException(ExceptionConstants.QG5);
        }
    }

    public int size() {
        return submitGameQuizzes.size();
    }

    public LocalDateTime lastSubmittedAt() {
        return lastSubmittedAt;
    }

    public int matchCount() {
        return (int) submitGameQuizzes.entrySet()
                .stream()
                .filter(entry -> entry.getKey().match(entry.getValue()))
                .count();
    }
}
