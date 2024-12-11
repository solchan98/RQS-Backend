package org.example.quizbox_deprecated.game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox_deprecated.quiz.QuizBuilder.quizBuilder;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quizbox_deprecated.game.domain.SubmitAnswer;
import org.example.quizbox.quizbox_deprecated.game.domain.SubmittedAnswers;
import org.example.quizbox.quizbox_deprecated.quiz.domain.Answer;
import org.example.quizbox.quizbox_deprecated.quiz.domain.Quiz;
import org.example.quizbox_deprecated.tags.QuizGameTag;
import org.junit.jupiter.api.Test;

class SubmittedAnswersTest {

    @QuizGameTag
    @Test
    void 동일_퀴즈에_대하여_중복_제출_불가() {
        SubmittedAnswers submittedAnswers = new SubmittedAnswers();
        Quiz quiz = quizBuilder().build();
        Set<Long> answerIds = quiz.getQuizAnswers().answers().stream().map(Answer::getId).collect(Collectors.toSet());
        SubmitAnswer submitAnswer = new SubmitAnswer(answerIds);
        submittedAnswers.submitAnswers(quiz.getId(), submitAnswer);

        Throwable throwable = catchThrowable(() -> submittedAnswers.submitAnswers(quiz.getId(), submitAnswer));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG5.code());
    }

}
