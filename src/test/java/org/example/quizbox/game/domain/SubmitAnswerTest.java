package org.example.quizbox_deprecated.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.SubmitAnswer;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SubmitAnswerTest {

    @Test
    void 답변은_최소_1개_이상() {
        Throwable throwable = catchThrowable(() -> new SubmitAnswer(Set.of()));
        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG6.code());
    }
}
