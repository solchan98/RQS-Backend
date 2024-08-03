package org.example.quizbox.game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;

import java.util.Set;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.SubmitAnswer;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.tags.QuizGameTag;
import org.junit.jupiter.api.Test;

class SubmitAnswerTest {

    @Test
    @QuizGameTag
    void 답변은_최소_1개_이상() {
        Throwable throwable = catchThrowable(() -> new SubmitAnswer(1L, 1L, Set.of()));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG6.code());
    }
}
