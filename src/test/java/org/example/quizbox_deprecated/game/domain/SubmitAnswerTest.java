package org.example.quizbox_deprecated.game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Set;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quizbox_deprecated.game.domain.SubmitAnswer;
import org.example.quizbox_deprecated.tags.QuizGameTag;
import org.junit.jupiter.api.Test;

class SubmitAnswerTest {

    @Test
    @QuizGameTag
    void 답변은_최소_1개_이상() {
        Throwable throwable = catchThrowable(() -> new SubmitAnswer(Set.of()));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG6.code());
    }
}
