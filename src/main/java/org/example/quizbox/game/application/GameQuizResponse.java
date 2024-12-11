package org.example.quizbox.game.application;

import org.example.quizbox.quiz.domain.Option;
import org.example.quizbox.quiz.domain.Quiz;

import java.util.Set;
import java.util.stream.Collectors;

public record GameQuizResponse(
        long quizId,
        String content,
        Set<GameQuizOptionResponse> options

) {
    public GameQuizResponse(Quiz quiz) {
        this(
                quiz.getId(),
                quiz.getContent().value(),
                quiz.getOptions()
                        .options()
                        .stream()
                        .map(GameQuizOptionResponse::new)
                        .collect(Collectors.toSet())
        );
    }
}

record GameQuizOptionResponse(
        long optionId,
        String content
) {
    public GameQuizOptionResponse(Option option) {
        this(option.getId(), option.getContent());
    }
}