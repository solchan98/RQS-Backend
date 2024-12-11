package org.example.quizbox.game.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.quizbox.quiz.domain.Option;
import org.example.quizbox.quiz.domain.Quiz;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(of = {"quizId"})
public class GameQuiz {

    @Getter
    private final long quizId;

    private final Set<Long> correctOptionIds = new HashSet<>();

    private final Set<Long> wrongOptionIds = new HashSet<>();

    public GameQuiz(long quizId, Set<Long> correctOptionIds, Set<Long> wrongOptionIds) {
        this.quizId = quizId;
        this.correctOptionIds.addAll(correctOptionIds);
        this.wrongOptionIds.addAll(wrongOptionIds);
    }

    public GameQuiz(Quiz quiz) {
        this.quizId = quiz.getId();
        this.correctOptionIds.addAll(quiz.getOptions().correctOptions().stream().map(Option::getId).collect(Collectors.toSet()));
        this.wrongOptionIds.addAll(quiz.getOptions().wrongOptions().stream().map(Option::getId).collect(Collectors.toSet()));
    }

    public boolean match(SubmitOption submitOption) {
        // match correct option
        return false;
    }
}
