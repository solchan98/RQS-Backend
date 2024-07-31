package org.example.quizbox.game.domain;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.quiz.domain.Quiz;

public class RemainGameQuizzes {

    private final List<Quiz> remainQuizzes;

    private GameQuizPicker gameQuizPicker;

    private Quiz quizWaitingSubmission;

    public RemainGameQuizzes(Collection<Quiz> quizzes, GameQuizPicker gameQuizPicker) {
        this.remainQuizzes = new ArrayList<>(quizzes);
        this.gameQuizPicker = gameQuizPicker;
    }

    public boolean waitingQuizBeSubmitted() {
        return quizWaitingSubmission != null;
    }

    public boolean isWaitingQuizBeSubmitted(Quiz quiz) {
        if (Objects.isNull(quizWaitingSubmission)) {
            return false;
        }

        return quizWaitingSubmission.equals(quiz);
    }

    public Optional<Quiz> pick() {
        if (waitingQuizBeSubmitted()) {
            throw new BusinessException(QG2);
        }

        Optional<Quiz> optionalQuiz = gameQuizPicker.pick(remainQuizzes);
        if (optionalQuiz.isEmpty()) {
            return Optional.empty();
        }

        Quiz quiz = optionalQuiz.get();
        this.quizWaitingSubmission = quiz;
        remainQuizzes.remove(quiz);

        return optionalQuiz;
    }

    public void clearWaitingQuiz() {
        this.quizWaitingSubmission = null;
    }
}
