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
    public QuizGame startGame(StartQuiz startQuiz) {
        GameQuizPicker gameQuizPicker = Optional.ofNullable(gameQuizPickerMap.get(startQuiz.quizPickStrategy()))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 타입"));

        QuizPack quizPack = quizPackRepository.findById(startQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));

        QuizGame quizGame = new QuizGame(quizPack, gameQuizPicker, startQuiz.memberId());
        return quizGameRepository.save(quizGame);
    }

    @Transactional
    public Optional<Quiz> pick(QuizGameId id) {
        // TODO 뽑을 때, 멤버 정보 받아서 게임 참가자인지 체크 하는 로직필요
        QuizGame quizGame = getQuizGameById(id);

        Optional<Quiz> optionalQuiz = quizGame.pick();
        quizGameRepository.save(quizGame);

        return optionalQuiz;
    }

    @Transactional
    public void submit(QuizGameId quizGameId, SubmitAnswer submitAnswer) {
        QuizGame quizGame = getQuizGameById(quizGameId);
        quizGame.submit(submitAnswer);

        quizGameRepository.save(quizGame);
    }

    private QuizGame getQuizGameById(QuizGameId id) {
        return quizGameRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QG7));
    }
}
