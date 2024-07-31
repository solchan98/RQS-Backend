package org.example.quizbox.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.SubmitAnswer;
import org.example.quizbox.game.domain.SubmittedAnswers;
import org.example.quizbox.quiz.domain.Answer;
import org.example.quizbox.quiz.domain.Quiz;
import org.junit.jupiter.api.Test;

class SubmittedAnswersTest {

    @Test
    void 동일_퀴즈에_대하여_중복_제출_불가() {
        Quiz quiz = quizBuilder().build();
        Set<Long> answerIds = quiz.getQuizAnswers().answers().stream().map(Answer::getId).collect(Collectors.toSet());
        SubmittedAnswers submittedAnswers = new SubmittedAnswers();
        submittedAnswers.submitAnswers(new SubmitAnswer(quiz, answerIds));

        Throwable throwable = catchThrowable(() -> submittedAnswers.submitAnswers(new SubmitAnswer(quiz, answerIds)));

        assertThat(throwable).isInstanceOf(BusinessException.class)
                .hasMessage(ExceptionConstants.QG5.code());
    }

}
