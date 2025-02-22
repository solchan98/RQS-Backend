package org.example.quizbox.quiz.presentation;

import org.example.quizbox.quiz.application.CreateOption;
import org.example.quizbox.quiz.application.CreateQuiz;

import java.util.Set;
import java.util.stream.Collectors;

public record CreateQuizRequest(
        String quizContent,
        Set<CreateOptionRequest> options
) {

    public CreateQuiz toCreateQuiz(long quizPackId, long memberId) {
        return new CreateQuiz(
                quizPackId,
                memberId,
                quizContent,
                options.stream().map(CreateOptionRequest::toCreateOption).collect(Collectors.toSet())
        );
    }
}

record CreateOptionRequest(
        String content,
        boolean correct
) {

    public CreateOption toCreateOption() {
        return new CreateOption(content, correct);
    }
}

