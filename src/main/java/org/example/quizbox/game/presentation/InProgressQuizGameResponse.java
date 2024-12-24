package org.example.quizbox.game.presentation;

import org.example.quizbox.game.domain.Game;
import org.example.quizbox.game.domain.GameId;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;

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

    public static InProgressQuizGameResponse from(Game game, QuizPack quizPack) {
        return new InProgressQuizGameResponse(
                game.getId().value(),
                quizPack.getTitle(),
                (int) game.submittedQuizSize(),
                (int) game.quizSize(),
                game.getCreatedAt(),
                game.lastSubmittedTime()
        );
    };

    public static Set<InProgressQuizGameResponse> dummy() {
        return Set.of(
                new InProgressQuizGameResponse(
                        GameId.create().value(),
                        "Dummy Quiz Pack Title",
                        5,
                        12,
                        LocalDateTime.now().minusDays(10),
                        LocalDateTime.now().minusHours(2)
                ),
                new InProgressQuizGameResponse(
                        GameId.create().value(),
                        "Dummy Quiz Pack Title",
                        3,
                        20,
                        LocalDateTime.now().minusDays(8),
                        LocalDateTime.now().minusHours(3)
                )

        );
    }
}
