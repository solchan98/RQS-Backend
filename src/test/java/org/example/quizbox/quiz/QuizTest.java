package org.example.quizbox.quiz;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.example.quizbox.quiz.domain.Answer;
import org.example.quizbox.quiz.domain.Quiz;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuizTest {

    @DisplayName("모두 맞아야 정답이며, 하나라도 정답이 아닐 경우 틀린다.")
    @Test
    void isMatched1() {
        Quiz quiz = QuizBuilder.quizBuilder()
                .options(
                        new Answer(1L, "A", true),
                        new Answer(2L, "B", true),
                        new Answer(3L, "C", true)
                )
                .build();

        assertThat(quiz.isMatched(Set.of(1L))).isFalse();
        assertThat(quiz.isMatched(Set.of(1L, 2L))).isFalse();
        assertThat(quiz.isMatched(Set.of(1L, 3L))).isFalse();
        assertThat(quiz.isMatched(Set.of(1L, 2L, 3L, 4L))).isFalse();
        assertThat(quiz.isMatched(Set.of(1L, 2L, 3L))).isTrue();
    }
}
