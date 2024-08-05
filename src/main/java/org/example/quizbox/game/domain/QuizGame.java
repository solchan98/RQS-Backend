package org.example.quizbox.game.domain;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG1;
import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG3;
import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG4;
import static org.example.quizbox.common.domain.exception.ExceptionConstants.QG9;

import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMember;

@AllArgsConstructor
public class QuizGame {

    private QuizGameId id;

    private QuizPack quizPack;

    private final RemainGameQuizzes remainGameQuizzes;
    private final SubmittedAnswers submittedAnswers = new SubmittedAnswers();

    private final QuizPackMember creator;

    public QuizGame(QuizPack quizPack, GameQuizPicker gameQuizPicker, long memberId) throws BusinessException {
        this.quizPack = quizPack;
        this.id = QuizGameId.create();
        if (Objects.isNull(quizPack)) {
            throw new BusinessException(QG1);
        }

        this.creator = quizPack.getQuizPackMemberById(memberId);
        this.remainGameQuizzes = new RemainGameQuizzes(quizPack.getQuizzes(), gameQuizPicker);
    }

    public QuizGameId id() {
        return id;
    }

    public Optional<Quiz> pick(long memberId) {
        if (!isParticipant(quizPack.getQuizPackMemberById(memberId))) {
            throw new BusinessException(QG9);
        }

        return remainGameQuizzes.pick();
    }

    public void submit(SubmitAnswer submitAnswer) {
        QuizPackMember quizPackMember = quizPack.getQuizPackMemberById(submitAnswer.memberId());
        if (!isParticipant(quizPackMember)) {
            throw new BusinessException(QG9);
        }

        Quiz quiz = quizPack.getQuizById(submitAnswer.quizId());
        if (!remainGameQuizzes.isWaitingQuizBeSubmitted(quiz)) {
            throw new BusinessException(QG3);
        }
        if (!quiz.containsAllAnswers(submitAnswer.answersIds())) {
            throw new BusinessException(QG4);
        }

        remainGameQuizzes.clearWaitingQuiz();
        submittedAnswers.submitAnswers(submitAnswer);
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
