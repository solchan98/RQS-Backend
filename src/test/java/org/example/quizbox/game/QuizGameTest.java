package org.example.quizbox.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

import java.util.List;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.QuizGame;
import org.example.quizbox.game.domain.SequentialGameQuizPicker;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.junit.jupiter.api.Test;

class QuizGameTest {

    private final SequentialGameQuizPicker sequentialGameQuizPicker = new SequentialGameQuizPicker();

    @Test
    void 퀴즈팩을_통해_게임을_시작() {
        assertThat(new QuizGame(quizPackBuilder().build(), sequentialGameQuizPicker)).isNotNull();
    }

    @Test
    void 퀴즈팩이_없는_경우_게임_불가() {
        Throwable throwable = catchThrowable(() -> new QuizGame(null, sequentialGameQuizPicker));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG1.code());
    }

    @Test
    void 게임에서_문제_뽑기_가능() {
        Quiz quiz = quizBuilder().id(1L).build();
        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker);

        assertThat(quizGame.pick()).contains(quiz);
    }

    @Test
    void 뽑을_문제가_없는_경우_빈_옵셔널_객체를_반환() {
        QuizGame quizGame = new QuizGame(quizPackBuilder().build(), sequentialGameQuizPicker);
        assertThat(quizGame.pick()).isEmpty();
    }
}
