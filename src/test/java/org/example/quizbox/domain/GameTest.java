package org.example.quizbox.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.domain.GameBuilder.builder;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM1;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM2;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM3;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM4;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM6;

import java.util.Set;
import org.example.quizbox.domain.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    Player defaultPlayer = new Player(1L, "sol");
    Set<Player> defaultPlayers = Set.of(defaultPlayer);

    @DisplayName("최소 1개 이상의 퀴즈를 가진다")
    @Test
    void game1() {
        Throwable throwable = catchThrowable(() -> builder().quizzes(SequentialGameQuizzes.from()).build());
        assertThat(throwable).isInstanceOf(RuntimeException.class).hasMessage(GM1.code());
        assertThat(builder().quizzes(SequentialGameQuizzes.from(QuizBuilder.builder().build())).build()
                .quizSize()).isPositive();
    }

    @DisplayName("퀴즈를 낼 수 있다.")
    @Test
    void game2() {
        Quiz quiz1 = QuizBuilder.builder().build();
        Quiz quiz2 = QuizBuilder.builder().build();
        Game game = builder().players(defaultPlayers).quizzes(SequentialGameQuizzes.from(quiz1, quiz2)).build();

        assertThat(game.nextQuiz(defaultPlayer)).contains(quiz1);
        assertThat(game.nextQuiz(defaultPlayer)).contains(quiz2);
    }

    @DisplayName("퀴즈를 모두 진행 후, 게임 결과를 확인할 수 있다.")
    @Test
    void game3() {
        GameQuizzes gameQuizzes = SequentialGameQuizzes.from(QuizBuilder.builder().build());
        gameQuizzes.nextQuiz();
        Game game = builder().players(defaultPlayers).quizzes(gameQuizzes).build();
        assertThat(game.report(defaultPlayer)).isNotNull();
    }

    @DisplayName("퀴즈가 남아있는 경우 게임 결과를 확인할 수 없다.")
    @Test
    void game4() {
        Game game = builder().players(defaultPlayers).quizzes(SequentialGameQuizzes.from(QuizBuilder.builder().build()))
                .build();
        Throwable throwable = catchThrowable(() -> game.report(defaultPlayer));
        assertThat(throwable).isInstanceOf(RuntimeException.class)
                .hasMessage(GM4.code());
    }

    @DisplayName("퀴즈에 대한 답을 제출하고 확인할 수 있다.")
    @Test
    void game6() {
        Quiz quiz = QuizBuilder.builder().id(1L).build();
        Game game = builder().players(defaultPlayers).quizzes(SequentialGameQuizzes.from(quiz)).build();
        game.nextQuiz(defaultPlayer);
        game.submitAnswer(quiz.getId(), new Answers(Set.of()), defaultPlayer);

        assertThat(game.isAnswerSubmitted(quiz.getId())).isTrue();
    }

    @DisplayName("지나간 퀴즈에 대한 답변 제출은 불가능하다.")
    @Test
    void game7() {
        Quiz quiz1 = QuizBuilder.builder().id(1L).build();
        Quiz quiz2 = QuizBuilder.builder().id(2L).build();
        Game game = builder().players(defaultPlayers).quizzes(SequentialGameQuizzes.from(quiz1, quiz2)).build();
        game.nextQuiz(defaultPlayer);
        game.nextQuiz(defaultPlayer);

        Throwable throwable = catchThrowable(() -> game.submitAnswer(quiz1.getId(), Answers.from(AnswerBuilder.builder()
                .buildForSubmit()), defaultPlayer));

        assertThat(throwable).isInstanceOf(RuntimeException.class)
                .hasMessage(GM3.code());
    }

    @DisplayName("이미 답변한 퀴즈는 중복 답변할 수 없다.")
    @Test
    void game8() {
        Quiz quiz1 = QuizBuilder.builder().id(1L).build();
        Quiz quiz2 = QuizBuilder.builder().id(2L).build();
        Game game = builder().players(defaultPlayers).quizzes(SequentialGameQuizzes.from(quiz1, quiz2)).build();
        game.nextQuiz(defaultPlayer);
        game.submitAnswer(quiz1.getId(), new Answers(Set.of()), defaultPlayer);

        Throwable throwable = catchThrowable(
                () -> game.submitAnswer(quiz1.getId(), new Answers(Set.of()), defaultPlayer));

        assertThat(throwable).isInstanceOf(RuntimeException.class)
                .hasMessage(GM2.code());
    }

    @DisplayName("게임 참여자인지 확인할 수 있다.")
    @Test
    void game9() {
        Player player = new Player(1L, "sol");
        Player nonPlayer = new Player(2L, "chan");
        Game game = builder().randomQuizzes().players(Set.of(player)).build();

        assertThat(game.isPlayer(player)).isTrue();
        assertThat(game.isPlayer(nonPlayer)).isFalse();
    }

    @DisplayName("게임 참여자가 아닌 경우 퀴즈를 뽑을 수 없다.")
    @Test
    void game10() {
        Game game = builder().players(defaultPlayers).randomQuizzes().build();
        Player nonPlayer = new Player(0L, "invalid user");

        Throwable throwable = catchThrowable(() -> game.nextQuiz(nonPlayer));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(GM6.code());
    }

    @DisplayName("게임 참여자가 아닌 경우 답변을 제출할 수 없다.")
    @Test
    void game11() {
        Game game = builder().players(defaultPlayers).randomQuizzes().build();
        Player nonPlayer = new Player(0L, "invalid user");

        Throwable throwable = catchThrowable(() -> game.submitAnswer(0, null, nonPlayer));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(GM6.code());
    }

    @DisplayName("게임 참여자가 아닌 경우 게임 결과를 리포팅할 수 없다.")
    @Test
    void game12() {
        Game game = builder().players(defaultPlayers).randomQuizzes().build();
        Player nonPlayer = new Player(0L, "invalid user");

        Throwable throwable = catchThrowable(() -> game.report(nonPlayer));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(GM6.code());
    }
}
