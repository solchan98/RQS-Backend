package org.example.quizbox.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.example.quizbox.domain.Game;
import org.example.quizbox.domain.Player;

public class InMemoryStorage {

    private static long quizId = 2;

    public static final Map<Long, Player> players = new HashMap<>(
            Map.of(
                    1L, new Player(1L, "sol"),
                    2L, new Player(2L, "chan")
            )
    );

    public static final Map<Long, QuizEntity> quizEntities = new HashMap<>(
            Map.of(
                    1L, new QuizEntity(1L, "quiz1",
                            Set.of(
                                    new AnswerEntity(1L, "correct answer1 of quiz1", true),
                                    new AnswerEntity(2L, "incorrect answer2 of quiz1", false)
                            )
                    ),
                    2L, new QuizEntity(2L, "quiz2",
                            Set.of(
                                    new AnswerEntity(3L, "correct answer1 of quiz2", true),
                                    new AnswerEntity(4L, "incorrect answer2 of quiz2", false)
                            )
                    )
            )
    );

    public static final Map<Long, Set<Long>> quizEntitiesByGroupId = new HashMap<>(
            Map.of(
                    1L, Set.of(1L, 2L)
            )
    );

    public static final Map<String, Game> games = new HashMap<>();

}
