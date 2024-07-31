package org.example.quizbox.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.QuizBuilder;
import org.junit.jupiter.api.Test;

class QuizPackTest {

    @Test
    void 동일_퀴즈팩에_퀴즈_이름_중복_불가() {
        QuizPack quizPack = new QuizPack(
                "title",
                List.of(QuizBuilder.quizBuilder().content("same").build())
        );

        Throwable throwable = catchThrowable(() -> quizPack.addQuiz(QuizBuilder.quizBuilder().content("same").build()));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP3.code());
    }

}
