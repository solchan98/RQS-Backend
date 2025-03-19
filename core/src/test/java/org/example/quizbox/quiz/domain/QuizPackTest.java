package org.example.quizbox.quiz.domain;

import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.example.quizbox.support.QuizBuilder;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.support.QuizBuilder.quizBuilder;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

class QuizPackTest {

    @Test
    void 퀴즈_생성_불가ㅡ퀴즈_이름_중복() {
        QuizContent sameContent = new QuizContent("name");
        QuizPackMember admin = new QuizPackMember(1L, QuizPackMemberRole.ADMIN);
        QuizPack quizPack = quizPackBuilder()
                .quizPackMembers(admin)
                .quizzes(Set.of(
                        quizBuilder()
                                .content(sameContent)
                                .build(admin)
                ))
                .build();
        Quiz sameContentQuiz = quizBuilder()
                .content(sameContent)
                .build(admin);

        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(sameContentQuiz));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP3.code());
    }

    @Test
    void 퀴즈_생성_불가ㅡ퀴즈팩_멤버_아닌_경우() {
        QuizPack quizPack = quizPackBuilder().build();
        QuizPackMember invalidQuizPackMember = new QuizPackMember(-999L, QuizPackMemberRole.ADMIN);

        Quiz invalidQuiz = quizBuilder()
                .build(invalidQuizPackMember);


        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(invalidQuiz));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP4.code());
    }

    @Test
    void 퀴즈_생성_불가ㅡ생성_권한_미보유() {
        QuizPackMember quizPackMember = new QuizPackMember(1L, QuizPackMemberRole.MEMBER);
        QuizPack quizPack = quizPackBuilder()
                .quizPackMembers(quizPackMember)
                .build();

        Quiz newQuiz = quizBuilder()
                .build(quizPackMember);

        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(newQuiz));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP5.code());
    }

    @Test
    void 퀴즈_조회_불가ㅡ퀴즈팩_멤버가_아닌_경우() {
        QuizPack quizPack = quizPackBuilder().build();

        QuizPackMember quizPackMember = new QuizPackMember(-999L, QuizPackMemberRole.MEMBER);

        Throwable throwable = catchThrowable(() -> quizPack.getQuizzes(quizPackMember));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP4.code());
    }

    @Test
    void 퀴즈는_최소_1개_이상() {
        Throwable throwable = catchThrowable(() -> quizPackBuilder().quizzes(Collections.emptyList()).build());

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP_MIN_QUIZ.code());
    }
}
