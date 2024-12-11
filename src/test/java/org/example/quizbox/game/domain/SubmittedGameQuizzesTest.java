package org.example.quizbox.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.Option;
import org.example.quizbox.quiz.domain.Quiz;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;

class SubmittedGameQuizzesTest {

    @Test
    void 동일_퀴즈에_대하여_중복_제출_불가() {
        SubmittedGameQuizzes submittedGameQuizzes = new SubmittedGameQuizzes();
        Quiz quiz = quizBuilder().build();
        Set<Long> optionIds = quiz.getOptions().options().stream().map(Option::getId).collect(Collectors.toSet());
        SubmitOption submitOption = new SubmitOption(optionIds);
        submittedGameQuizzes.submitOptions(new GameQuiz(quiz), submitOption);

        Throwable throwable = catchThrowable(() -> submittedGameQuizzes.submitOptions(new GameQuiz(quiz), submitOption));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG5.code());
    }

}
