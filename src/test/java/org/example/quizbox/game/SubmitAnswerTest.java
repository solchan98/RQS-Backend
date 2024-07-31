package org.example.quizbox.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;

import java.util.Set;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.SubmitAnswer;
import org.example.quizbox.quiz.domain.Quiz;
import org.junit.jupiter.api.Test;

class SubmitAnswerTest {

    @Test
    void 답변은_최소_1개_이상() {
        Throwable throwable = catchThrowable(() -> new SubmitAnswer(quizBuilder().build(), Set.of()));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG6.code());

    }

    @Test
    void 퀴즈의_보기가_아닌_값은_답_제출_불가() {
        Quiz quiz = quizBuilder().id(1L).build();
        Throwable throwable = catchThrowable(() -> new SubmitAnswer(quiz, Set.of(-999L)));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG4.code());
    }

}
