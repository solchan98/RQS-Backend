package org.example.quizbox.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuizTest {

    @DisplayName("모두 맞아야 정답이며, 하나라도 정답이 아닐 경우 틀린다.")
    @Test
    void isMatched1() {
        AnswerBuilder answer1Builder = AnswerBuilder.builder().content("A");
        AnswerBuilder answer2Builder = AnswerBuilder.builder().content("B");
        AnswerBuilder answer3Builder = AnswerBuilder.builder().content("C");

        Quiz quiz = QuizBuilder.builder().answers(
                answer1Builder.correct(true).buildForQuiz(),
                answer2Builder.correct(true).buildForQuiz(),
                answer3Builder.correct(true).buildForQuiz()
        ).build();

        assertThat(quiz.isMatched(Answers.from(answer1Builder.buildForSubmit()))).isFalse();
        assertThat(quiz.isMatched(
                Answers.from(
                        answer1Builder.buildForSubmit(),
                        answer2Builder.buildForSubmit()
                ))).isFalse();
        assertThat(quiz.isMatched(
                Answers.from(
                        answer1Builder.buildForSubmit(),
                        answer3Builder.buildForSubmit()
                ))).isFalse();
        assertThat(quiz.isMatched(
                Answers.from(
                        answer1Builder.buildForSubmit(),
                        answer2Builder.buildForSubmit(),
                        answer3Builder.buildForSubmit()
                ))).isTrue();
    }

    @DisplayName("퀴즈의 정답과 정답이 아닌 답변을 한번에 조회할 수 있다.")
    @Test
    void answers() {
        AnswerBuilder answer1Builder = AnswerBuilder.builder().content("A");
        AnswerBuilder answer2Builder = AnswerBuilder.builder().content("B");
        AnswerBuilder answer3Builder = AnswerBuilder.builder().content("C");
        AnswerBuilder answer4Builder = AnswerBuilder.builder().content("D");

        Quiz quiz = QuizBuilder.builder().answers(
                Answers.from(
                        answer1Builder.correct(true).buildForQuiz(),
                        answer2Builder.correct(true).buildForQuiz(),
                        answer3Builder.correct(true).buildForQuiz(),
                        answer4Builder.correct(true).buildForQuiz())
        ).build();

        assertThat(quiz.getAnswers()).isEqualTo(
                Answers.from(
                        answer1Builder.buildForSubmit(),
                        answer2Builder.buildForSubmit(),
                        answer3Builder.buildForSubmit(),
                        answer4Builder.buildForSubmit()
                )
        );
    }
}
