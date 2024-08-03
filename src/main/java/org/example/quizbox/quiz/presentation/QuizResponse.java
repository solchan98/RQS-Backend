package org.example.quizbox.quiz.presentation;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.quizbox.quiz.domain.Quiz;

public record QuizResponse(
        long id,
        QuizPackMemberResponse creator,
        String content,
        Set<QuizAnswerResponse> answers
) {

    public static QuizResponse from(Quiz quiz) {
        Set<QuizAnswerResponse> answerResponses = quiz.getQuizAnswers().answers().stream()
                .map(QuizAnswerResponse::from)
                .collect(Collectors.toSet());

        return new QuizResponse(
                quiz.getId(),
                QuizPackMemberResponse.from(quiz.getCreator()),
                quiz.getContent().value(),
                answerResponses
        );
    }

}
