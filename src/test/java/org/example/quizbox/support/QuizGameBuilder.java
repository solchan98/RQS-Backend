package org.example.quizbox.support;

import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.quizbox.game.domain.GameQuizPicker;
import org.example.quizbox.game.domain.QuizGame;
import org.example.quizbox.game.domain.SequentialGameQuizPicker;
import org.example.quizbox.game.domain.SubmitAnswer;
import org.example.quizbox.quiz.domain.Answer;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;

public class QuizGameBuilder {

    private QuizPack quizPack = quizPackBuilder()
            .quizzes(
                    List.of(
                            quizBuilder().build(),
                            quizBuilder().build()
                    )
            )
            .build();

    private GameQuizPicker gameQuizPicker = new SequentialGameQuizPicker();

    public static QuizGameBuilder quizGameBuilder() {
        return new QuizGameBuilder();
    }

    public QuizGameBuilder quizPack(QuizPack quizPack) {
        this.quizPack = quizPack;
        return this;
    }

    public QuizGameBuilder gameQuizPicker(GameQuizPicker gameQuizPicker) {
        this.gameQuizPicker = gameQuizPicker;
        return this;
    }

    public QuizGame build() {
        return new QuizGame(quizPack, gameQuizPicker);
    }

    public QuizGame build(long passQuizCount) {
        QuizGame quizGame = build();

        for (int idx = 0; idx < passQuizCount; idx++) {
            Optional<Quiz> pick = quizGame.pick();
            if (pick.isEmpty()) {
                continue;
            }
            Quiz quiz = pick.get();
            var answerIds = quiz.getQuizAnswers().answers().stream().map(Answer::getId).collect(Collectors.toSet());

            quizGame.submit(new SubmitAnswer(quiz, answerIds));
        }

        return quizGame;
    }
}
