package org.example.quizbox.quiz.presentation;

import java.util.Collection;

public record QuizPackResponse( // TODO ...
        String quizPackId,
        int quizCount,
        int playCount,
        Collection<TagResponse> tags
) {
}
