package org.example.quizbox.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SequentialGameQuizzesTest {

    @DisplayName("제출 가능한 퀴즈가 존재하는지 확인할 수 있다.")
    @Test
    void existsNextQuiz1() {
        SequentialGameQuizzes expectedTrue = SequentialGameQuizzes.from(QuizBuilder.builder().build());
        SequentialGameQuizzes expectedFalse = SequentialGameQuizzes.from();

        assertThat(expectedTrue.existsNextQuiz()).isTrue();
        assertThat(expectedFalse.existsNextQuiz()).isFalse();
    }

    @DisplayName("다음 퀴즈를 뽑을 수 있으며, 없는 경우 빈 Optional Object 반환한다.")
    @Test
    void nextQuiz1() {
        SequentialGameQuizzes quizzes = SequentialGameQuizzes.from(QuizBuilder.builder().build());

        assertThat(quizzes.nextQuiz()).isPresent();
        assertThat(quizzes.nextQuiz()).isEmpty();
    }

    @DisplayName("퀴즈는 생성 순서대로 뽑는다.")
    @Test
    void nextQuiz2() {
        Quiz quiz1 = QuizBuilder.builder().id(1).build();
        Quiz quiz2 = QuizBuilder.builder().id(2).build();
        Quiz quiz3 = QuizBuilder.builder().id(3).build();
        Quiz quiz4 = QuizBuilder.builder().id(4).build();
        SequentialGameQuizzes quizzes = SequentialGameQuizzes.from(quiz1, quiz2, quiz3, quiz4);

        assertThat(quizzes.nextQuiz()).containsSame(quiz1);
        assertThat(quizzes.nextQuiz()).containsSame(quiz2);
        assertThat(quizzes.nextQuiz()).containsSame(quiz3);
        assertThat(quizzes.nextQuiz()).containsSame(quiz4);
    }

}
