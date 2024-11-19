package org.example.quizbox.game.presentation;

import org.example.quizbox.game.domain.QuizGameId;

import java.time.LocalDateTime;

public record OnGoingQuizGameResponse(
        String id,
        String quizPackTitle,
        int submittedQuizCount,
        int quizCount,
        LocalDateTime startedAt,
        LocalDateTime lastSubmittedAt
) {

    public static OnGoingQuizGameResponse dummy() {
        return new OnGoingQuizGameResponse(
                QuizGameId.create().value(),
                "Dummy Quiz Pack Title",
                5,
                12,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusHours(2)
        );
    }
}
