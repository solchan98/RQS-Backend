package org.example.quizbox.game.domain;

import lombok.Getter;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.*;

public class Game {

    @Getter
    private final GameId id = GameId.create();

    @Getter
    private final long quizPackId;

    private final RemainGameQuizzes remainGameQuizzes;

    private final SubmittedAnswers submittedAnswers = new SubmittedAnswers();

    @Getter
    private final long creatorId;

    private GameQuiz waitingGameQuiz;

    public Game(QuizPack quizPack, long creatorId, GameQuizPicker quizPicker) {
        Set<Quiz> quizzes = quizPack.getQuizzes();
        Set<GameQuiz> gameQuizzes = quizzes.stream()
                .map(GameQuiz::new)
                .collect(Collectors.toSet());

        this.quizPackId = quizPack.getId();
        this.remainGameQuizzes = new RemainGameQuizzes(gameQuizzes, quizPicker);
        this.creatorId = creatorId;
    }

    /**
     * @param creatorId 멤버 아이디
     * @return 새로 뽑은 퀴즈 아이디
     */
    public GameQuiz pick(long creatorId) {
        // 게임 생성자인가? QG9
        validateIsCreator(creatorId);
        // 제출 대기 상태가 아닌가? QG2
        if (isWaitingQuiz()) {
            throw new BusinessException(QG2);
        }

        GameQuiz gameQuiz = remainGameQuizzes.pick()
                .orElseThrow(() -> new RuntimeException("더 이상 뽑을 퀴즈가 없습니다."));
        this.waitingGameQuiz = gameQuiz;

        return gameQuiz;
    }

    public void submit(long creatorId, SubmitAnswer submitAnswer) {
        // 게임 생성자인가? QG9
        validateIsCreator(creatorId);
        // 제출 대기 상태인가? QG3
        if (!isWaitingQuiz()) {
            throw new BusinessException(QG3);
        }
        submittedAnswers.submitAnswers(waitingGameQuiz, submitAnswer);
        clearWaitingGameQuiz();
    }

    private boolean isWaitingQuiz() {
        return waitingGameQuiz != null;
    }

    private void clearWaitingGameQuiz() {
        this.waitingGameQuiz = null;
    }

    private void validateIsCreator(long creatorId) {
        boolean isCreator = this.creatorId == creatorId;
        if (!isCreator) {
            throw new BusinessException(QG9);
        }
    }

    public long quizSize() {
        if (isWaitingQuiz()) {
            return remainGameQuizzes.size() + submittedAnswers.size() + 1;
        }

        return remainGameQuizzes.size() + submittedAnswers.size();
    }

    public long remainingQuizSize() {
        return remainGameQuizzes.size();
    }

    public long submittedQuizSize() {
        return submittedAnswers.size();
    }

    public LocalDateTime lastSubmittedTime() {
        return submittedAnswers.lastSubmittedAt();
    }
}
