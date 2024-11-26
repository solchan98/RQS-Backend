package org.example.quizbox.game.domain;

import org.example.quizbox.quiz.domain.Quiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RemainGameQuizzes {

    private final List<Quiz> remainQuizzes;

    private GameQuizPicker gameQuizPicker;

    public RemainGameQuizzes(Collection<Quiz> quizzes, GameQuizPicker gameQuizPicker) {
        this.remainQuizzes = new ArrayList<>(quizzes);
        this.gameQuizPicker = gameQuizPicker;
    }

    public Optional<Quiz> pick() {
//        if (waitingQuizBeSubmitted()) {
//            throw new BusinessException(QG2);
//        }

        Optional<Quiz> optionalQuiz = gameQuizPicker.pick(remainQuizzes);
        if (optionalQuiz.isEmpty()) {
            return Optional.empty();
        }

        Quiz quiz = optionalQuiz.get();
//        this.quizWaitingSubmission = quiz;
        remainQuizzes.remove(quiz);

        return optionalQuiz;
    }

    public int remainQuizSize() {
        return remainQuizzes.size();
    }
}
