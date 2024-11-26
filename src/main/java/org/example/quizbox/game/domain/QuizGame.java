package org.example.quizbox.game.domain;

import lombok.AllArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMember;

import java.util.Objects;
import java.util.Optional;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.*;

@AllArgsConstructor
public class QuizGame {

    private final QuizGameId id;

    private final QuizPack quizPack;

    private final RemainGameQuizzes remainGameQuizzes;
    private final SubmittedAnswers submittedAnswers = new SubmittedAnswers();

    private final QuizPackMember creator;

    private Quiz quizWaitingSubmit;

    public QuizGame(QuizPack quizPack, GameQuizPicker gameQuizPicker, long memberId) throws BusinessException {
        this.quizPack = quizPack;
        this.id = QuizGameId.create();
        if (Objects.isNull(quizPack)) {
            throw new BusinessException(QG1);
        }

        this.creator = quizPack.validateIsMember(memberId);
        this.remainGameQuizzes = new RemainGameQuizzes(quizPack.getQuizzes(), gameQuizPicker);
    }

    public QuizGameId id() {
        return id;
    }

    public Optional<Quiz> pick(long memberId) {
        if (!isParticipant(quizPack.validateIsMember(memberId))) {
            throw new BusinessException(QG9);
        }

        if (quizWaitingSubmit != null) {
            throw new BusinessException(QG2);
        }
        Optional<Quiz> optionalQuiz = remainGameQuizzes.pick();
        optionalQuiz.ifPresent(quiz -> quizWaitingSubmit = quiz);

        return optionalQuiz;
    }

    public void submit(long memberId, SubmitAnswer submitAnswer) {
        QuizPackMember quizPackMember = quizPack.validateIsMember(memberId);
        if (!isParticipant(quizPackMember)) {
            throw new BusinessException(QG9);
        }

        if (quizWaitingSubmit == null) {
            throw new BusinessException(QG3);
        }

        if (!quizWaitingSubmit.containsAllAnswers(submitAnswer.answersIds())) {
            throw new BusinessException(QG4);
        }

        submittedAnswers.submitAnswers(quizWaitingSubmit.getId(), submitAnswer);
        quizWaitingSubmit = null;
    }

    public boolean isParticipant(QuizPackMember quizPackMember) {
        return this.creator.equals(quizPackMember);
    }

    public QuizGameStatus status() {
        return new QuizGameStatus(
                id,
                quizPack.getId(),
                creator.getMemberId(),
                (int) quizPack.quizSize(),
                remainQuizSize(),
                submittedQuizSize()
        );
    }

    public int remainQuizSize() {
        return remainGameQuizzes.remainQuizSize();
    }

    public int submittedQuizSize() {
        return submittedAnswers.submittedQuizSize();
    }
}
