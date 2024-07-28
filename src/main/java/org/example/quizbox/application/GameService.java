package org.example.quizbox.application;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.domain.Game;
import org.example.quizbox.domain.GameQuizzes;
import org.example.quizbox.domain.GameReporter;
import org.example.quizbox.domain.GameRepository;
import org.example.quizbox.domain.Player;
import org.example.quizbox.domain.PlayerRepository;
import org.example.quizbox.domain.QuizRepository;
import org.example.quizbox.domain.SequentialGameQuizzes;
import org.example.quizbox.domain.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final PlayerRepository playerRepository;
    private final QuizRepository quizRepository;
    private final GameRepository gameRepository;

    @Transactional
    public String startGame(long playerId, long groupId) {
        Player player = playerRepository.findById(playerId).orElseThrow(BusinessException::checkPlayer);
        GameQuizzes gameQuizzes = SequentialGameQuizzes.from(quizRepository.findAllByGroupId(groupId));
        Game game = new Game(gameQuizzes, Set.of(player), new GameReporter());

        return gameRepository.save(game).getId();
    }

    @Transactional
    public GameStatus gameStatus(String gameId) {
        return gameRepository.findById(gameId)
                .map(GameStatus::from)
                .stream()
                .findAny()
                .orElseThrow(BusinessException::gameDoesNotExist);
    }
}
