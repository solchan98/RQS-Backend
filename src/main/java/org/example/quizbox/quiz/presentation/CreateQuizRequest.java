package org.example.quizbox.quiz.presentation;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.quizbox.quiz.application.CreateAnswer;
import org.example.quizbox.quiz.application.CreateQuiz;

public record CreateQuizRequest(
        String quizContent,
        Set<CreateAnswerRequest> answers
) {

    public CreateQuiz toCreateQuiz(long quizPackId) {
        return new CreateQuiz(
                quizPackId,
                quizContent,
                answers.stream().map(CreateAnswerRequest::toCreateAnswer).collect(Collectors.toSet())
        );
    }
}

record CreateAnswerRequest(
        String content,
        boolean correct
) {

    public CreateAnswer toCreateAnswer() {
        return new CreateAnswer(content, correct);
    }
}

