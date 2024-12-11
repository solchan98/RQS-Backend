package org.example.quizbox.game.application;

import org.example.quizbox.quiz.domain.Answer;
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
                quiz.getAnswers()
                        .answers()
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
    public GameQuizOptionResponse(Answer answer) {
        this(answer.getId(), answer.getContent());
    }
}