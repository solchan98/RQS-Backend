package org.example.quizbox.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.quizbox.domain.Answer.forQuizAnswer;
import static org.example.quizbox.domain.Answer.forSubmitAnswer;

import java.util.Set;
import org.junit.jupiter.api.Test;

class GameE2ETest {

    Player defaultPlayer = new Player(1L, "sol");
    Set<Player> defaultPlayers = Set.of(defaultPlayer);

    @Test
    void game1() {

        Answers quiz1Answers = Answers.from(
                forQuizAnswer(1L, "2", true),
                forQuizAnswer(2L, "1", false),
                forQuizAnswer(3L, "3", false),
                forQuizAnswer(4L, "4", false)
        );
        Quiz quiz1 = QuizBuilder.builder()
                .id(1L)
                .content("1 + 1 = ?")
                .answers(quiz1Answers)
                .build();

        Answers quiz2Answers = Answers.from(
                forQuizAnswer(5L, "5", true),
                forQuizAnswer(6L, "2", false),
                forQuizAnswer(7L, "3", false),
                forQuizAnswer(8L, "4", false)
        );
        Quiz quiz2 = QuizBuilder.builder()
                .id(2L)
                .content("1 * 5 = ?")
                .answers(quiz2Answers)
                .build();

        Answers quiz3Answers = Answers.from(
                forQuizAnswer(9L, "8", true),
                forQuizAnswer(10L, "2", false),
                forQuizAnswer(11L, "3", false),
                forQuizAnswer(12L, "4", false)
        );
        Quiz quiz3 = QuizBuilder.builder()
                .id(3L)
                .content("2 * 4 = ?")
                .answers(quiz3Answers)
                .build();

        GameQuizzes gameQuizzes = SequentialGameQuizzes.from(quiz1, quiz2, quiz3);

        Game game = GameBuilder.builder().players(defaultPlayers).quizzes(gameQuizzes).build();

        game.nextQuiz(defaultPlayer);
        game.submitAnswer(quiz1.getId(), Answers.from(forSubmitAnswer(1L, "2")), defaultPlayer);

        game.nextQuiz(defaultPlayer);
        game.submitAnswer(quiz2.getId(), Answers.from(forSubmitAnswer(5L, "5")), defaultPlayer);

        game.nextQuiz(defaultPlayer);
        game.submitAnswer(quiz3.getId(), Answers.from(forSubmitAnswer(8L, "8")), defaultPlayer);

        GameReport report = game.report(defaultPlayer);

        assertThat(report.quizCount()).isEqualTo(3);
        assertThat(report.matchCount()).isEqualTo(2);
    }

}
