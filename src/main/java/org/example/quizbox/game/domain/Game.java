package org.example.quizbox.game.domain;

import lombok.Getter;
import org.example.quizbox.common.Audit;
import org.example.quizbox.common.BusinessException;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.quizbox.common.ExceptionConstants.*;

public class Game extends Audit {

    @Getter
    private final GameId id = GameId.create();

    @Getter
    private final long quizPackId;

    private final RemainGameQuizzes remainGameQuizzes;

    private final SubmittedGameQuizzes submittedGameQuizzes = new SubmittedGameQuizzes();

    @Getter
    private final long creatorId;

    @Getter
    private GameQuiz waitingGameQuiz;

    public Game(QuizPack quizPack, long creatorId, GameQuizPicker quizPicker) {
        Set<Quiz> quizzes = quizPack.getQuizzes(quizPack.getQuizPackMemberBy(creatorId));
        Set<GameQuiz> gameQuizzes = quizzes.stream()
                .map(GameQuiz::new)
                .collect(Collectors.toSet());

        this.quizPackId = quizPack.getId();
        this.remainGameQuizzes = new RemainGameQuizzes(gameQuizzes, quizPicker);
        this.creatorId = creatorId;
        this.setCreatedBy(creatorId);
        this.setUpdatedBy(creatorId);
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
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
            return waitingGameQuiz;
        }

        GameQuiz gameQuiz = remainGameQuizzes.pick()
                .orElseThrow(() -> new BusinessException(QG11));
        this.waitingGameQuiz = gameQuiz;

        return gameQuiz;
    }

    public void submit(long creatorId, SubmitOption submitOption) {
        // 게임 생성자인가? QG9
        validateIsCreator(creatorId);
        // 제출 대기 상태인가? QG3
        if (!isWaitingQuiz()) {
            throw new BusinessException(QG3);
        }
        submittedGameQuizzes.submitOptions(waitingGameQuiz, submitOption);
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
            return remainGameQuizzes.size() + submittedGameQuizzes.size() + 1;
        }

        return remainGameQuizzes.size() + submittedGameQuizzes.size();
    }

    public long remainingQuizSize() {
        return remainGameQuizzes.size();
    }

    public long submittedQuizSize() {
        return submittedGameQuizzes.size();
    }

    public LocalDateTime lastSubmittedTime() {
        return submittedGameQuizzes.lastSubmittedAt();
    }

    public boolean isEnd() {
        return waitingGameQuiz == null && remainGameQuizzes.size() == 0;
    }

    public GameHistory getGameHistory() {
        return new GameHistory(id, quizPackId, creatorId, submittedGameQuizzes.size(), submittedGameQuizzes.matchCount());
    }
}
