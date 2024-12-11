package org.example.quizbox.game.domain;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.Answer;
import org.example.quizbox.quiz.domain.Quiz;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;

class SubmittedAnswersTest {

    @Test
    void 동일_퀴즈에_대하여_중복_제출_불가() {
        SubmittedAnswers submittedAnswers = new SubmittedAnswers();
        Quiz quiz = quizBuilder().build();
        Set<Long> answerIds = quiz.getAnswers().answers().stream().map(Answer::getId).collect(Collectors.toSet());
        SubmitAnswer submitAnswer = new SubmitAnswer(answerIds);
        submittedAnswers.submitAnswers(new GameQuiz(quiz), submitAnswer);

        Throwable throwable = catchThrowable(() -> submittedAnswers.submitAnswers(new GameQuiz(quiz), submitAnswer));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG5.code());
    }

}
