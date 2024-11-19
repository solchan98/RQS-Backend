package org.example.quizbox.quiz.presentation;

import org.example.quizbox.subscriptions.TagResponse;

import java.util.Collection;

public record QuizPackResponse( // TODO ...
        String quizPackId,
        int quizCount,
        int playCount,
        Collection<TagResponse> tags
) {
}
