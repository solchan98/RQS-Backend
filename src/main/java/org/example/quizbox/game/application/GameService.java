package org.example.quizbox.game.application;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.*;
import org.example.quizbox.quiz.domain.IQuizPackRepository;
import org.example.quizbox.quiz.domain.IQuizQueryRepository;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.QP1;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final IQuizPackRepository quizPackRepository;
    private final IQuizQueryRepository quizQueryRepository;
    private final Map<QuizPickStrategy, GameQuizPicker> gameQuizPickerMap;


    public GameService(GameRepository gameRepository, IQuizQueryRepository quizQueryRepository, IQuizPackRepository quizPackRepository,
                       Map<String, GameQuizPicker> gameQuizPickerMap) {
        this.gameRepository = gameRepository;
        this.quizQueryRepository = quizQueryRepository;
        this.quizPackRepository = quizPackRepository;
        this.gameQuizPickerMap = gameQuizPickerMap.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> QuizPickStrategy.valueOf(entry.getKey()), Map.Entry::getValue));
    }

    @Transactional
    public GameStatusResponse start(StartQuiz startQuiz) {
        QuizPack quizPack = quizPackRepository.findById(startQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(QP1));
        quizPack.getQuizPackMemberBy(startQuiz.memberId());
        GameQuizPicker gameQuizPicker = Optional.ofNullable(gameQuizPickerMap.get(startQuiz.quizPickStrategy()))
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QG10));

        Game game = new Game(quizPack, startQuiz.memberId(), gameQuizPicker);
        gameRepository.save(game);

        return GameStatusResponse.from(game, null);
    }

    @Transactional
    public GameQuizResponse pick(long memberId, GameId gameId) {
        Game game = getQuizGameById(gameId);
        GameQuiz gameQuiz = game.pick(memberId);
        gameRepository.save(game);

        Quiz quiz = quizQueryRepository.getById(gameQuiz.getQuizId());

        return new GameQuizResponse(quiz);
    }

    @Transactional
    public void submit(long memberId, GameId gameId, SubmitOption submitOption) {
        Game game = getQuizGameById(gameId);
        game.submit(memberId, submitOption);

        gameRepository.save(game);
    }

    private Game getQuizGameById(GameId id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QG7));
    }

    @Transactional(readOnly = true)
    public GameStatusResponse gameStatus(GameId gameId) {
        Game game = getQuizGameById(gameId);
        if (game.getWaitingGameQuiz() == null) {
            return GameStatusResponse.from(game, null);
        }

        // TODO: 게임 진행 중도에 퀴즈 삭제 시, 핸들링 처리 설계 추가 필요
        return GameStatusResponse.from(
                game,
                new GameQuizResponse(quizQueryRepository.getById(game.getWaitingGameQuiz().getQuizId()))
        );
    }
}
