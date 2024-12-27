package org.example.quizbox.game.application;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.*;
import org.example.quizbox.game.presentation.InProgressQuizGameResponse;
import org.example.quizbox.quiz.domain.IQuizPackRepository;
import org.example.quizbox.quiz.domain.IQuizQueryRepository;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.presentation.QuizResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.QP1;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final IQuizPackRepository quizPackRepository;
    private final IQuizQueryRepository quizQueryRepository;
    private final IGameHistoryRepository gameHistoryRepository;
    private final Map<QuizPickStrategy, GameQuizPicker> gameQuizPickerMap;


    public GameService(GameRepository gameRepository, IQuizQueryRepository quizQueryRepository, IQuizPackRepository quizPackRepository, IGameHistoryRepository gameHistoryRepository,
                       Map<String, GameQuizPicker> gameQuizPickerMap) {
        this.gameRepository = gameRepository;
        this.quizQueryRepository = quizQueryRepository;
        this.quizPackRepository = quizPackRepository;
        this.gameHistoryRepository = gameHistoryRepository;
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

        return new GameQuizResponse(game, QuizResponse.from(quiz));
    }

    @Transactional
    public void submit(long memberId, GameId gameId, SubmitOption submitOption) {
        Game game = getQuizGameById(gameId);
        game.submit(memberId, submitOption);

        gameRepository.save(game);
        if (game.isEnd()) {
            GameHistory gameHistory = game.getGameHistory();
            gameHistoryRepository.save(gameHistory);
            gameRepository.deleteBy(gameId);
        }
    }

    @Transactional(readOnly = true)
    public GameHistory getGameResult(GameId gameId) {
        return gameHistoryRepository.findBy(gameId)
                .orElseThrow(() -> new RuntimeException("게임 결과가 존재하지 않습니다."));

    }

    private Game getQuizGameById(GameId id) {
        return gameRepository.findBy(id)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QG7));
    }

    @Transactional(readOnly = true)
    public Set<InProgressQuizGameResponse> getInProgressQuizGames(
            long memberId
    ) {
        List<Game> games = gameRepository.findAllBy(memberId);

        return games.stream()
                .map(game ->
                        InProgressQuizGameResponse.from(
                                game,
                                quizPackRepository.findById(game.getQuizPackId()) // TODO: 진행중인 게임이 있을 때, 퀴즈팩이 제거되면 어떻게 처리할지 고민하기
                                        .orElseThrow(() -> new BusinessException(QP1))
                        )
                ).collect(Collectors.toSet());
    }
}
