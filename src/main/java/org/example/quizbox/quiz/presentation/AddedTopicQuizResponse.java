package org.example.quizbox.quiz.presentation;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public record AddedTopicQuizResponse(
        Set<Long> quizIds,
        String tag // TODO: String -> TagResponse?
) {

    public static Collection<AddedTopicQuizResponse> dummy() {
        return List.of(
                new AddedTopicQuizResponse(Set.of(1L, 4L, 5L), "모니터링"),
                new AddedTopicQuizResponse(Set.of(2L), "Backend"),
                new AddedTopicQuizResponse(Set.of(3L), "CI/CD"),
                new AddedTopicQuizResponse(Set.of(1L, 4L), "ELK")
        );
    }
}
