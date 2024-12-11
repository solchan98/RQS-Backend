package org.example.quizbox.quiz.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

class QuizPackTest {

    @Test
    void 퀴즈_생성_불가ㅡ퀴즈_이름_중복() {
        QuizPackMember adminMember = new QuizPackMember(1L, QuizPackMemberRole.ADMIN);
        QuizPack quizPack = new QuizPack("title", Set.of(adminMember), Set.of());

        Quiz newQuiz = new Quiz(adminMember, new QuizContent("same"), Set.of(Option.falseOption("A"), Option.trueOption("B")));
        quizPack.addQuiz(newQuiz);

        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(newQuiz));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP3.code());
    }

    @Test
    void 퀴즈_생성_불가ㅡ퀴즈팩_멤버_아닌_경우() {
        QuizPack quizPack = quizPackBuilder().build();
        QuizPackMember invalidQuizPackMember = new QuizPackMember(-999L, QuizPackMemberRole.ADMIN);

        Quiz newQuiz = new Quiz(invalidQuizPackMember, new QuizContent("same"), Set.of(Option.falseOption("A"), Option.trueOption("B")));

        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(newQuiz));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP4.code());
    }

    @Test
    void 퀴즈_생성_불가ㅡ생성_권한_미보유() {
        QuizPackMember quizPackMember = new QuizPackMember(1L, QuizPackMemberRole.MEMBER);
        QuizPack quizPack = quizPackBuilder()
                .quizPackMembers(quizPackMember)
                .build();

        Quiz newQuiz = new Quiz(quizPackMember, new QuizContent("same"), Set.of(Option.falseOption("A"), Option.trueOption("B")));

        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(newQuiz));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP5.code());
    }
//
//    @Test
//    void 초대장이_유효하면_새로운_퀴즈팩_멤버_추가_가능() {
//
//    }
//
//
//    @Test
//    void 초대장이_유효하지_않으면_새로운_퀴즈팩_멤버_추가_불가() {
//
//    }


}
