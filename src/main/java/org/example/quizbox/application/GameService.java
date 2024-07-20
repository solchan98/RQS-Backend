package org.example.quizbox.application;

import static org.example.quizbox.domain.exception.ExceptionConstants.GM5;
import static org.example.quizbox.domain.exception.ExceptionConstants.GM7;

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

@Service
@RequiredArgsConstructor
public class GameService {

    private final PlayerRepository playerRepository;
    private final QuizRepository quizRepository;
    private final GameRepository gameRepository;

    public String startGame(long playerId, long groupId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new BusinessException(GM7.code()));
        GameQuizzes gameQuizzes = SequentialGameQuizzes.from(quizRepository.findAllByGroupId(groupId));
        Game game = new Game(gameQuizzes, Set.of(player), new GameReporter());

        return gameRepository.save(game).getId();
    }

    public GameStatus gameStatus(String gameId) {
        return gameRepository.findById(gameId)
                .map(GameStatus::from)
                .stream()
                .findAny()
                .orElseThrow(() -> new BusinessException(GM5.code()));
    }
}
