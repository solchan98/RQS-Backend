package org.example.quizbox.quiz.application;

import java.util.Set;

public record CreateQuiz(
        long quizPackId,
        long memberId,
        String quizContent,
        Set<CreateAnswer> answers

) {

}
