package org.example.quizbox_deprecated.game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.example.quizbox.quizbox_deprecated.game.domain.SequentialGameQuizPicker;
import org.example.quizbox_deprecated.quiz.QuizBuilder;
import org.example.quizbox.quizbox_deprecated.quiz.domain.Quiz;
import org.example.quizbox_deprecated.tags.QuizGameTag;
import org.junit.jupiter.api.Test;

class SequentialGameQuizPickerTest {

    final SequentialGameQuizPicker sequentialGameQuizPicker = new SequentialGameQuizPicker();

    @Test
    @QuizGameTag
    void 뽑는_순서는_ID가_가장_작은_퀴즈() {
        Quiz quiz3 = QuizBuilder.quizBuilder().id(3L).build();
        Quiz quiz1 = QuizBuilder.quizBuilder().id(1L).build();
        Quiz quiz2 = QuizBuilder.quizBuilder().id(2L).build();

        assertThat(sequentialGameQuizPicker.pick(Set.of(quiz3, quiz1, quiz2))).contains(quiz1);
        assertThat(sequentialGameQuizPicker.pick(Set.of(quiz3, quiz2))).contains(quiz2);
        assertThat(sequentialGameQuizPicker.pick(Set.of(quiz3))).contains(quiz3);
    }

    @Test
    @QuizGameTag
    void 뽑을_퀴즈가_없으면_빈_옵셔널_반환() {
        assertThat(sequentialGameQuizPicker.pick(List.of())).isEmpty();
    }

}
