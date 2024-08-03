package org.example.quizbox.game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;

import java.util.Set;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.RemainGameQuizzes;
import org.example.quizbox.game.domain.SequentialGameQuizPicker;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.tags.QuizGameTag;
import org.junit.jupiter.api.Test;

class RemainGameQuizzesTest {

    SequentialGameQuizPicker sequentialGameQuizPicker = new SequentialGameQuizPicker();

    @Test
    @QuizGameTag
    void 답변_대기중인_경우_다음_문제_뽑기_불가() {
        Quiz quiz1 = quizBuilder().build();
        Quiz quiz2 = quizBuilder().build();
        RemainGameQuizzes remainGameQuizzes = new RemainGameQuizzes(Set.of(quiz1, quiz2), sequentialGameQuizPicker);
        remainGameQuizzes.pick();

        Throwable throwable = catchThrowable(remainGameQuizzes::pick);

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG2.code());
    }

    @Test
    @QuizGameTag
    void 답변_대기중인_문제가_없는_경우_새로운_퀴즈_뽑기_가능() {
        Quiz quiz1 = quizBuilder().build();
        Quiz quiz2 = quizBuilder().build();
        RemainGameQuizzes remainGameQuizzes = new RemainGameQuizzes(Set.of(quiz1, quiz2), sequentialGameQuizPicker);
        remainGameQuizzes.clearWaitingQuiz();

        assertThat(remainGameQuizzes.pick()).isNotEmpty();
    }

    @Test
    @QuizGameTag
    void 답변_대기중인_상태_초기화_가능() {
        Quiz quiz1 = quizBuilder().build();
        Quiz quiz2 = quizBuilder().build();
        RemainGameQuizzes remainGameQuizzes = new RemainGameQuizzes(Set.of(quiz1, quiz2), sequentialGameQuizPicker);
        remainGameQuizzes.pick();

        remainGameQuizzes.clearWaitingQuiz();

        assertThat(remainGameQuizzes.waitingQuizBeSubmitted()).isFalse();
    }

    @Test
    @QuizGameTag
    void 뽑을_문제가_없는_경우_빈_옵셔널_객체를_반환() {
        RemainGameQuizzes remainGameQuizzes = new RemainGameQuizzes(Set.of(), sequentialGameQuizPicker);
        assertThat(remainGameQuizzes.pick()).isEmpty();
    }
}
