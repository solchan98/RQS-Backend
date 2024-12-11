package org.example.quizbox_deprecated.quiz.domain2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Set;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.Answer;
import org.example.quizbox.quiz.domain.QuizAnswers;
import org.junit.jupiter.api.Test;

class QuizAnswersTest {

    @Test
    void 퀴즈_답변은_최소_2개_이상() {
        Throwable throwable = catchThrowable(() -> new QuizAnswers(Set.of()));

        assertThat(throwable).isInstanceOf(BusinessException.class).hasMessage(ExceptionConstants.QA1.code());
        assertThat(new QuizAnswers(Set.of(Answer.falseOption("A"), Answer.trueOption("B")))).isNotNull();
    }

    @Test
    void 퀴즈_답변_중_정답은_최소_1개_이상() {
        Throwable throwable = catchThrowable(() -> new QuizAnswers(Set.of(Answer.falseOption("A"), Answer.falseOption("B"))));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QA2.code());

    }
}
