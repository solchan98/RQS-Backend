package org.example.quizbox.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.junit.jupiter.api.Test;

class QuizPickerTest {

    IQuizPackRepository quizPackRepository = mock(IQuizPackRepository.class);

    QuizPicker quizPicker = new QuizPicker(quizPackRepository);


    @Test
    void 퀴즈팩_멤버_아닌_경우_퀴즈_조회_불가() {
        long quizPackId = 1;
        long quizId = 1;
        long memberId = 1;
        given(quizPackRepository.findQuizPackMemberByIdAndMemberId(quizPackId, memberId))
                .willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> quizPicker.getQuiz(quizPackId, quizId, memberId));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP4.code());
    }

    @Test
    void 없는_퀴즈는_조회_불가() {
        long quizPackId = 1;
        long quizId = -999;
        long memberId = 1;
        given(quizPackRepository.findQuizPackMemberByIdAndMemberId(quizPackId, memberId))
                .willReturn(Optional.of(mock(QuizPackMember.class)));
        given(quizPackRepository.findQuizByIdAndQuizId(quizPackId, quizId))
                .willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> quizPicker.getQuiz(quizPackId, quizId, memberId));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP6.code());
    }

}
