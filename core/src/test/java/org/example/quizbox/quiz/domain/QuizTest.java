package org.example.quizbox.quiz.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.quizbox.support.QuizBuilder.quizBuilder;

class QuizTest {

    @DisplayName("퀴즈의 정답 유무를 확인할 수 있다.")
    @Test
    void match1() {
        QuizPackMember admin = new QuizPackMember(1L, QuizPackMemberRole.ADMIN);
        Option optionA = new Option(1L, "A", true);
        Option optionB = new Option(2L, "B", true);
        Quiz quiz = quizBuilder()
                .options(optionA, optionB)
                .build(admin);

        Set<Long> matchOptionIds = Set.of(optionA.getId(), optionB.getId());
        Set<Long> noMatchOptionIds = Set.of(optionA.getId());

        assertThat(quiz.match(matchOptionIds)).isTrue();
        assertThat(quiz.match(noMatchOptionIds)).isFalse();
    }

    @DisplayName("content가 같은지 확인할 수 있다.")
    @Test
    void isSameContent1() {
        QuizPackMember admin = new QuizPackMember(1L, QuizPackMemberRole.ADMIN);
        QuizContent content = new QuizContent("content");

        Quiz quiz1 = quizBuilder().content(content).build(admin);
        Quiz quiz2 = quizBuilder().content(content).build(admin);
        Quiz quiz3 = quizBuilder().content(new QuizContent("not equals")).build(admin);

        assertThat(quiz1.isSameContent(quiz2)).isTrue();
        assertThat(quiz1.isSameContent(quiz3)).isFalse();
    }
}
