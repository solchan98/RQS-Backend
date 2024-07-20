package org.example.quizbox.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameReporterTest {

    GameReporter gameReporter = new GameReporter();

    @DisplayName("퀴즈와 답변을 통해 결과를 리포트한다.")
    @Test
    void report1() {
        AnswerBuilder answer1Builder = AnswerBuilder.builder().content("A");
        AnswerBuilder answer2Builder = AnswerBuilder.builder().content("B");
        Quiz quiz1 = QuizBuilder.builder().id(1L).answers(
                answer1Builder.correct(true).buildForQuiz(),
                answer2Builder.correct(true).buildForQuiz()
        ).build();
        Quiz quiz2 = QuizBuilder.builder().id(2L).answers(
                answer1Builder.correct(true).buildForQuiz(),
                answer2Builder.correct(true).buildForQuiz()
        ).build();
        Map<Long, Answers> selectedAnswers = Map.of(
                quiz1.getId(), Answers.from(answer1Builder.buildForSubmit()),
                quiz2.getId(), Answers.from(answer1Builder.buildForSubmit(), answer2Builder.buildForSubmit())
        );

        GameReport gameReport = gameReporter.report(SequentialGameQuizzes.from(quiz1, quiz2), selectedAnswers);

        assertThat(gameReport).isNotNull();
        assertThat(gameReport.quizCount()).isEqualTo(2);
        assertThat(gameReport.matchCount()).isEqualTo(1);
    }
}
