package org.example.quizbox.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;
import static org.example.quizbox.support.QuizAnswersBuilder.quizAnswersBuilder;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

import java.util.List;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.junit.jupiter.api.Test;

class QuizPackTest {

    @Test
    void 퀴즈_생성_불가ㅡ퀴즈_이름_중복() {
        QuizPackMember quizPackMember = new QuizPackMember(1L, QuizPackMemberRole.allRoles());
        QuizPack quizPack = new QuizPack(
                "title",
                new QuizPackMembers(quizPackMember),
                List.of(quizBuilder().content("same").build())
        );
        QuizContent sameContent = new QuizContent("same");

        Throwable throwable = catchThrowable(
                () -> quizPack.createQuiz(quizPackMember.getMemberId(), sameContent, quizAnswersBuilder().build()));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP3.code());
    }

    @Test
    void 퀴즈_생성_불가ㅡ퀴즈팩_멤버_아닌_경우() {
        QuizPack quizPack = quizPackBuilder().build();

        Throwable throwable = catchThrowable(
                () -> quizPack.createQuiz(-999L, new QuizContent("content"), quizAnswersBuilder().build()));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP4.code());
    }

    @Test
    void 퀴즈_생성_불가ㅡ생성_권한_미보유() {
        QuizPack quizPack = quizPackBuilder().build();

        Throwable throwable = catchThrowable(
                () -> quizPack.createQuiz(-999L, new QuizContent("content"), quizAnswersBuilder().build()));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP4.code());
    }

    @Test
    void 초대장이_유효하면_새로운_퀴즈팩_멤버_추가_가능() {

    }


    @Test
    void 초대장이_유효하지_않으면_새로운_퀴즈팩_멤버_추가_불가() {

    }


}
