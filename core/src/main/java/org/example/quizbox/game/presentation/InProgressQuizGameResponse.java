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
        LocalDateTime lastUpdatedAt
) {

    public static InProgressQuizGameResponse from(Game game, QuizPack quizPack) {
        return new InProgressQuizGameResponse(
                game.getId().value(),
                quizPack.getTitle(),
                (int) game.submittedQuizSize(),
                (int) game.quizSize(),
                game.lastSubmittedTime() != null ? game.lastSubmittedTime() : game.getCreatedAt()
        );
    };
}
