package org.example.quizbox.quiz.domain;

import static org.assertj.core.api.Assertions.*;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;
import static org.example.quizbox.support.QuizAnswersBuilder.quizAnswersBuilder;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

import java.util.List;
import java.util.Set;

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
                List.of(quizBuilder().content("same").build()),
                Set.of()
        );
        QuizContent sameContent = new QuizContent("same");
        Quiz newQuiz = Quiz.create(quizPackMember, sameContent, quizAnswersBuilder().build());

        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(newQuiz));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP3.code());
    }

    @Test
    void 퀴즈_생성_불가ㅡ퀴즈팩_멤버_아닌_경우() {
        QuizPack quizPack = quizPackBuilder().build();
        QuizPackMember invaludQuizPackMember = QuizPackMember.createAdmin(-999L);
        Quiz newQuiz = Quiz.create(invaludQuizPackMember, new QuizContent("content"), quizAnswersBuilder().build());

        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(newQuiz));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP4.code());
    }

    @Test
    void 퀴즈_생성_불가ㅡ생성_권한_미보유() {
        QuizPack quizPack = quizPackBuilder().build();
        QuizPackMember invalidQuizPackMember = QuizPackMember.createAdmin(-999L);
        Quiz newQuiz = Quiz.create(invalidQuizPackMember, new QuizContent("content"), quizAnswersBuilder().build());
        Throwable throwable = catchThrowable(
                () -> quizPack.addQuiz(newQuiz));

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
