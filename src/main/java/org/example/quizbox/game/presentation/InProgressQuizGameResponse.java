package org.example.quizbox.game.presentation;

import org.example.quizbox.game.domain.QuizGameId;

import java.time.LocalDateTime;
import java.util.Set;

public record InProgressQuizGameResponse(
        String id,
        String quizPackTitle,
        int submittedQuizCount,
        int quizCount,
        LocalDateTime startedAt,
        LocalDateTime lastSubmittedAt
) {

    public static Set<InProgressQuizGameResponse> dummy() {
        return Set.of(
                new InProgressQuizGameResponse(
                        QuizGameId.create().value(),
                        "Dummy Quiz Pack Title",
                        5,
                        12,
                        LocalDateTime.now().minusDays(10),
                        LocalDateTime.now().minusHours(2)
                ),
                new InProgressQuizGameResponse(
                        QuizGameId.create().value(),
                        "Dummy Quiz Pack Title",
                        3,
                        20,
                        LocalDateTime.now().minusDays(8),
                        LocalDateTime.now().minusHours(3)
                )

        );
    }
}
