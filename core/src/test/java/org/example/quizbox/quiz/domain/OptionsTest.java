package org.example.quizbox.quiz.domain;

import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OptionsTest {

    @DisplayName("퀴즈의 답변은 최소 2개 이상")
    @Test
    void constructor1() {
        Throwable throwable = catchThrowable(() -> new Options(Set.of()));

        assertThat(throwable).isInstanceOf(BusinessException.class).hasMessage(ExceptionConstants.QA1.code());
        assertThat(new Options(Set.of(Option.falseOption("A"), Option.trueOption("B")))).isNotNull();
    }

    @DisplayName("퀴즈의 정답은 최소 1개 이상")
    @Test
    void constructor2() {
        Throwable throwable = catchThrowable(() -> new Options(Set.of(Option.falseOption("A"), Option.falseOption("B"))));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QA2.code());
    }

    @DisplayName("답변과 보기가 완전히 일치해야 정답이다.")
    @Test
    void match1() {
        Option optionA = new Option(1L, "A", true);
        Option optionB = new Option(2L, "B", true);
        Options options = new Options(
                Set.of(optionA, optionB)
        );
        Set<Long> allMatchOptions = Set.of(
                optionA.getId(), optionB.getId()
        );
        Set<Long> oneMatchOptions = Set.of(
                optionA.getId()
        );

        assertThat(options.match(allMatchOptions)).isTrue();
        assertThat(options.match(oneMatchOptions)).isFalse();
    }

    @DisplayName("정답인 옵션만 조회할 수 있다.")
    @Test
    void correctOptions1() {
        Option correctOptions = new Option(1L, "A", true);
        Option incorrectOptions = new Option(2L, "B", false);
        Options options = new Options(
                Set.of(correctOptions, incorrectOptions)
        );

        assertThat(options.correctOptions())
                .hasSize(1)
                .contains(correctOptions)
                .doesNotContain(incorrectOptions);
    }

    @DisplayName("오답인 옵션만 조회할 수 있다.")
    @Test
    void wrongOptions1() {
        Option correctOptions = new Option(1L, "A", true);
        Option incorrectOptions = new Option(2L, "B", false);
        Options options = new Options(
                Set.of(correctOptions, incorrectOptions)
        );

        assertThat(options.wrongOptions())
                .hasSize(1)
                .contains(incorrectOptions)
                .doesNotContain(correctOptions);
    }
}
