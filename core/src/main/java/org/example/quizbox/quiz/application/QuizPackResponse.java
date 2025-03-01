package org.example.quizbox.quiz.application;

import org.example.quizbox.keyword.domain.Keyword;
import org.example.quizbox.keyword.presentation.KeywordResponse;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.example.quizbox.quiz.presentation.QuizPackMemberResponse;
import org.example.quizbox.quiz.presentation.QuizResponse;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record QuizPackResponse(
        long quizPackId,
        String quizPackTitle,
        Set<QuizPackMemberResponse> quizPackMembers,
        Set<QuizResponse> quizzes,
        Collection<KeywordResponse> keywords
) {

    public static QuizPackResponse of(
            QuizPack quizPack,
            Set<QuizPackMember> quizPackMembers,
            Set<Quiz> quizzes,
            Collection<Keyword> keywords
    ) {
        return new QuizPackResponse(
                quizPack.getId(),
                quizPack.getTitle(),
                quizPackMembers.stream()
                        .map(QuizPackMemberResponse::from)
                        .collect(Collectors.toSet()),
                quizzes.stream()
                        .map(quiz -> QuizResponse.from(quiz, true))
                        .collect(Collectors.toSet()),
                keywords.stream()
                        .map(KeywordResponse::from)
                        .collect(Collectors.toSet())
        );
    }
}
