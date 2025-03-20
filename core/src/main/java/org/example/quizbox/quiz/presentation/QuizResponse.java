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

    public static QuizResponse from(Quiz quiz, boolean withOptionCorrect) {
        Set<OptionResponse> optionResponses = quiz.getOptions().readonlyValues().stream()
                .map(option -> {
                    if (withOptionCorrect) {
                        return OptionResponse.from(option);
                    }

                    return OptionResponse.fromOptionWithoutCorrect(option);
                })
                .collect(Collectors.toSet());

        return new QuizResponse(
                quiz.getId(),
                quiz.getContent().value(),
                QuizPackMemberResponse.from(quiz.getQuizPackMember()),
                optionResponses
        );
    }
}

@Getter
@AllArgsConstructor
class OptionResponse {

    private long optionId;
    private String content;
    private Boolean correct;

    public static OptionResponse from(Option option) {
        return new OptionResponse(
                option.getId(),
                option.getContent(),
                option.isCorrect()
        );
    }

    public static OptionResponse fromOptionWithoutCorrect(Option option) {
        return new OptionResponse(
                option.getId(),
                option.getContent(),
                null
        );
    }
}
