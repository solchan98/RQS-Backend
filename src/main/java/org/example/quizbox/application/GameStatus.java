package org.example.quizbox.application;

import org.example.quizbox.domain.Game;
import org.example.quizbox.domain.GameQuizzes;

public record GameStatus(
        String id,
        int quizCount,
        int remainQuizCount
) {

    public static GameStatus from(Game game) {
        GameQuizzes gameQuizzes = game.getGameQuizzes();
        return new GameStatus(game.getId(), gameQuizzes.size(), gameQuizzes.remainQuizCount());
    }


}
