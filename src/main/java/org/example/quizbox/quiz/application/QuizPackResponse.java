package org.example.quizbox.quiz.application;

import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.example.quizbox.quiz.presentation.QuizPackMemberResponse;
import org.example.quizbox.quiz.presentation.QuizResponse;
import org.example.quizbox.quiz.presentation.TagResponse;
import org.example.quizbox.tag.domain.Tag;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record QuizPackResponse(
        long quizPackId,
        String quizPackTitle,
        Set<QuizPackMemberResponse> quizPackMembers,
        Set<QuizResponse> quizzes,
        Collection<TagResponse> tags
) {

    public static QuizPackResponse of(QuizPack quizPack, Set<QuizPackMember> quizPackMembers, Set<Quiz> quizzes, Collection<Tag> tags) {
        return new QuizPackResponse(
                quizPack.getId(),
                quizPack.getTitle(),
                quizPackMembers.stream()
                        .map(QuizPackMemberResponse::from)
                        .collect(Collectors.toSet()),
                quizzes.stream()
                        .map(QuizResponse::from)
                        .collect(Collectors.toSet()),
                tags.stream()
                        .map(TagResponse::from)
                        .collect(Collectors.toSet())
        );
    }
}
