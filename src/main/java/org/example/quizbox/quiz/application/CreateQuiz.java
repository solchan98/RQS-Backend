package org.example.quizbox.quiz.application;

import java.util.Set;

public record CreateQuiz(
        long quizPackId,
        String quizContent,
        Set<CreateAnswer> answers

) {

}
