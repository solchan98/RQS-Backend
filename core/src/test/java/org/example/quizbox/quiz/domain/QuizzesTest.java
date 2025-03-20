package org.example.quizbox.quiz.domain;

import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class QuizzesTest {

    @DisplayName("퀴즈는 최소 1개 이상")
    @Test
    void constructor1() {

        assertThat(catchThrowable(() -> new Quizzes(Set.of())))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QP_MIN_QUIZ.code());
    }

}
