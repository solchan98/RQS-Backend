package org.example.quizbox.game.application;

import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.*;
import org.example.quizbox.quiz.domain.IQuizPackQueryRepository;
import org.example.quizbox.quiz.domain.IQuizQueryRepository;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final IQuizQueryRepository quizQueryRepository;
    private final IQuizPackQueryRepository quizPackQueryRepository;

    private final Map<QuizPickStrategy, GameQuizPicker> gameQuizPickerMap;

    public GameService(GameRepository gameRepository, IQuizQueryRepository quizQueryRepository, IQuizPackQueryRepository quizPackQueryRepository,
                       Map<String, GameQuizPicker> gameQuizPickerMap) {
        this.gameRepository = gameRepository;
        this.quizQueryRepository = quizQueryRepository;
        this.quizPackQueryRepository = quizPackQueryRepository;
        this.gameQuizPickerMap = gameQuizPickerMap.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> QuizPickStrategy.valueOf(entry.getKey()), Map.Entry::getValue));
    }

    @Transactional
    public GameStatusResponse start(StartQuiz startQuiz) {
        QuizPack quizPack = quizPackQueryRepository.getById(startQuiz.quizPackId());
        quizPack.validateIsMember(startQuiz.memberId());
        GameQuizPicker gameQuizPicker = Optional.ofNullable(gameQuizPickerMap.get(startQuiz.quizPickStrategy()))
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QG10));

        Game game = new Game(quizPack, startQuiz.memberId(), gameQuizPicker);
        gameRepository.save(game);

        return GameStatusResponse.from(game);
    }

    @Transactional
    public GameQuizResponse pick(long memberId, GameId gameId) {
        Game game = getQuizGameById(gameId);
        GameQuiz gameQuiz = game.pick(memberId);
        gameRepository.save(game);
        ;

        Quiz quiz = quizQueryRepository.getQuizById(gameQuiz.getQuizId());

        return new GameQuizResponse(quiz);
    }

    @Transactional
    public void submit(long memberId, GameId gameId, SubmitAnswer submitAnswer) {
        Game game = getQuizGameById(gameId);
        game.submit(memberId, submitAnswer);

        gameRepository.save(game);
    }

    private Game getQuizGameById(GameId id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QG7));
    }

    @Transactional(readOnly = true)
    public GameStatusResponse gameStatus(GameId gameId) {
        Game game = getQuizGameById(gameId);

        return GameStatusResponse.from(game);
    }
}
