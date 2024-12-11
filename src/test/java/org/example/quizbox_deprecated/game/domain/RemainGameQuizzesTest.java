package org.example.quizbox_deprecated.game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.example.quizbox.quizbox_deprecated.game.domain.RemainGameQuizzes;
import org.example.quizbox.quizbox_deprecated.game.domain.SequentialGameQuizPicker;
import org.example.quizbox_deprecated.tags.QuizGameTag;
import org.junit.jupiter.api.Test;

class RemainGameQuizzesTest {

    SequentialGameQuizPicker sequentialGameQuizPicker = new SequentialGameQuizPicker();

    @Test
    @QuizGameTag
    void 뽑을_문제가_없는_경우_빈_옵셔널_객체를_반환() {
        RemainGameQuizzes remainGameQuizzes = new RemainGameQuizzes(Set.of(), sequentialGameQuizPicker);
        assertThat(remainGameQuizzes.pick()).isEmpty();
    }
}
