package org.example.quizbox.game.domain;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GameQuizTest {

    @Test
    void match_제출_옵션이_모두_정답이면_매치() {
        Set<Long> answers = Set.of(1L, 2L);
        GameQuiz gameQuiz = new GameQuiz(1L, answers, Set.of(3L, 4L));
        SubmitOption submitOption = new SubmitOption(answers);

        assertThat(gameQuiz.match(submitOption)).isTrue();
    }

    @Test
    void match_제출_옵션이_하나라도_맞지_않으면_실패() {
        GameQuiz gameQuiz = new GameQuiz(1L, Set.of(1L, 2L), Set.of(3L, 4L));
        SubmitOption submitOption = new SubmitOption(Set.of(1L, 2L, 3L));

        assertThat(gameQuiz.match(submitOption)).isFalse();
    }

}
