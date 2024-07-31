package org.example.quizbox.game.domain;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG1;
import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG3;

import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;

@AllArgsConstructor
public class QuizGame {

    private final RemainGameQuizzes remainGameQuizzes;
    private final SubmittedAnswers submittedAnswers = new SubmittedAnswers();

    public QuizGame(QuizPack quizPack, GameQuizPicker gameQuizPicker) throws BusinessException {
        if (Objects.isNull(quizPack)) {
            throw new BusinessException(QG1);
        }

        this.remainGameQuizzes = new RemainGameQuizzes(quizPack.getQuizzes(), gameQuizPicker);
    }

    public Optional<Quiz> pick() {
        return remainGameQuizzes.pick();
    }

    public void submit(SubmitAnswer submitAnswer) {
        if (!remainGameQuizzes.isWaitingQuizBeSubmitted(submitAnswer.quiz())) {
            throw new BusinessException(QG3);
        }
        remainGameQuizzes.clearWaitingQuiz();
        submittedAnswers.submitAnswers(submitAnswer);
    }
}
