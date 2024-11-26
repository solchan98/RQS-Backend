package org.example.quizbox.game.application;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.game.domain.GameQuizPicker;
import org.example.quizbox.game.domain.QuizGame;
import org.example.quizbox.game.domain.QuizGameId;
import org.example.quizbox.game.domain.QuizGameRepository;
import org.example.quizbox.game.domain.QuizGameStatus;
import org.example.quizbox.game.domain.SubmitAnswer;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.infrastructure.QuizPackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizGameService {

    private final QuizGameRepository quizGameRepository;

    private final QuizPackRepository quizPackRepository;

    private final Map<QuizPickStrategy, GameQuizPicker> gameQuizPickerMap;

    public QuizGameService(QuizGameRepository quizGameRepository, QuizPackRepository quizPackRepository,
            Map<String, GameQuizPicker> gameQuizPickerMap) {
        this.quizGameRepository = quizGameRepository;
        this.quizPackRepository = quizPackRepository;
        this.gameQuizPickerMap = gameQuizPickerMap.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> QuizPickStrategy.valueOf(entry.getKey()), Entry::getValue));
    }

    @Transactional
    public QuizGameStatus startGame(StartQuiz startQuiz) {
        GameQuizPicker gameQuizPicker = Optional.ofNullable(gameQuizPickerMap.get(startQuiz.quizPickStrategy()))
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QG10));

        QuizPack quizPack = quizPackRepository.findById(startQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));

        QuizGame quizGame = new QuizGame(quizPack, gameQuizPicker, startQuiz.memberId());
        return quizGameRepository.save(quizGame).status();
    }

    @Transactional
    public Optional<Quiz> pick(long memberId, QuizGameId quizGameId) {
        QuizGame quizGame = getQuizGameById(quizGameId);

        Optional<Quiz> optionalQuiz = quizGame.pick(memberId);
        quizGameRepository.save(quizGame);

        return optionalQuiz;
    }

    @Transactional
    public void submit(long memberId, QuizGameId quizGameId, SubmitAnswer submitAnswer) {
        QuizGame quizGame = getQuizGameById(quizGameId);
        quizGame.submit(memberId, submitAnswer);

        quizGameRepository.save(quizGame);
    }

    @Transactional(readOnly = true)
    public QuizGameStatus status(QuizGameId quizGameId) {
        return getQuizGameById(quizGameId).status();
    }

    private QuizGame getQuizGameById(QuizGameId id) {
        return quizGameRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QG7));
    }
}
