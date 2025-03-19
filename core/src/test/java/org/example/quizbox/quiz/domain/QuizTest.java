package org.example.quizbox.quiz.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.quizbox.support.QuizBuilder.quizBuilder;

class QuizTest {

    @DisplayName("모두 맞아야 정답이며, 하나라도 정답이 아닐 경우 틀린다.")
    @Test
    void match1() {
        QuizPackMember admin = new QuizPackMember(1L, QuizPackMemberRole.ADMIN);
        Quiz quiz = quizBuilder()
                .options(
                        new Option(1L, "A", true),
                        new Option(2L, "B", true),
                        new Option(3L, "C", true)
                )
                .build(admin);

        assertThat(quiz.match(Set.of(1L))).isFalse();
        assertThat(quiz.match(Set.of(1L, 2L))).isFalse();
        assertThat(quiz.match(Set.of(1L, 3L))).isFalse();
        assertThat(quiz.match(Set.of(1L, 2L, 3L, 4L))).isFalse();
        assertThat(quiz.match(Set.of(1L, 2L, 3L))).isTrue();
    }
}
