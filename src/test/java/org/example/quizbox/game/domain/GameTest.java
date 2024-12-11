package org.example.quizbox.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG11;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

class GameTest {

    // TODO 테스트 코드에서 사용되는 멤버 정보 빌러로 빼서 리팩토링 진행하기
    private final SequentialGameQuizPicker sequentialGameQuizPicker = new SequentialGameQuizPicker();

    @Test
    void 퀴즈팩을_통해_게임을_시작() {
        assertThat(new Game(quizPackBuilder().build(), 1L, sequentialGameQuizPicker)).isNotNull();
    }

    @Test
    void 게임에서_문제_뽑기_가능() {
        Quiz quiz = quizBuilder().id(1L).build();
        QuizPack quizPack = quizPackBuilder().quizzes(Set.of(quiz)).build();
        Game game = new Game(quizPack, 1L, sequentialGameQuizPicker);

        assertThat(game.pick(1L)).isEqualTo(new GameQuiz(quiz));
    }

    @Test
    void 뽑을_문제가_없는_경우_예외() {
        Game game = new Game(quizPackBuilder().build(), 1L, sequentialGameQuizPicker);
        Throwable throwable = catchThrowable(() -> game.pick(1L));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(QG11.code());
    }

    @Test
    void 퀴즈팩_멤버가_아닌_경우_문제_뽑기_불가() {
        Quiz quiz = quizBuilder().build();
        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
        Game game = new Game(quizPack, 1L, sequentialGameQuizPicker);

        Throwable throwable = catchThrowable(() -> game.pick(-999L));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG9.code());
    }

    @Test
    void 퀴즈_참가자가_아닌_경우_문제_뽑기_불가() {
        Quiz quiz = quizBuilder().id(1L).build();
        QuizPack quizPack = quizPackBuilder()
                .quizzes(List.of(quiz))
                .quizPackMembers(
                        new QuizPackMembers(
                                Set.of(
                                        new QuizPackMember(1L, QuizPackMemberRole.ADMIN),
                                        new QuizPackMember(2L, QuizPackMemberRole.ADMIN)
                                )
                        )
                ).build();
        Game game = new Game(quizPack, 1L, sequentialGameQuizPicker);

        Throwable throwable = catchThrowable(() -> game.pick(2L));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG9.code());
    }


    @Test
    void 참가자가_아닌_경우_답변_불가() {
        Quiz quiz = quizBuilder().build();
        QuizPack quizPack = quizPackBuilder()
                .quizzes(List.of(quiz))
                .quizPackMembers(
                        new QuizPackMembers(
                                Set.of(
                                        new QuizPackMember(1L, QuizPackMemberRole.ADMIN),
                                        new QuizPackMember(2L, QuizPackMemberRole.ADMIN)
                                )
                        )
                ).build();
        Game game = new Game(quizPack, 1L, sequentialGameQuizPicker);
        game.pick(1L);
        Set<Long> optionIds = quiz.getOptions().options().stream().map(Option::getId).collect(Collectors.toSet());

        Throwable throwable = catchThrowable(
                () -> game.submit(2, new SubmitOption(optionIds)));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG9.code());
    }


    @Test
    void 답변_대기중인_경우_다음_문제_뽑기_불가() {
        Quiz quiz = quizBuilder().build();
        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
        Game game = new Game(quizPack, 1L, sequentialGameQuizPicker);
        game.pick(1L);

        Throwable throwable = catchThrowable(() -> game.pick(1L));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG2.code());
    }

    @Test
    void 답변_대기중인_문제가_없는_경우_새로운_퀴즈_뽑기_가능() {
        Quiz quiz = quizBuilder().build();
        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
        Game game = new Game(quizPack, 1L, sequentialGameQuizPicker);

        assertThatNoException().isThrownBy(() -> game.pick(1L));
    }
}
