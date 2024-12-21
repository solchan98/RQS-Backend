package org.example.quizbox.quiz.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.quizbox.quiz.domain.Option;
import org.example.quizbox.quiz.domain.Quiz;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class QuizResponse {

    private long quizId;

    private String content;

    private QuizPackMemberResponse creator;

    private Set<OptionResponse> options;

    public static QuizResponse from(Quiz quiz) {
        return new QuizResponse(
                quiz.getId(),
                quiz.getContent().value(),
                QuizPackMemberResponse.from(quiz.getQuizPackMember()),
                quiz.getOptions().getValues().stream()
                        .map(OptionResponse::from)
                        .collect(Collectors.toSet())
        );
    }
}

@Getter
@AllArgsConstructor
class OptionResponse {
    private long optionId;

    private String content;

    public static OptionResponse from(Option option) {
        return new OptionResponse(
                option.getId(),
                option.getContent()
        );
    }
}
