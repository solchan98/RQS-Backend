package org.example.quizbox.quiz.presentation;

import io.jsonwebtoken.lang.Collections;
import org.example.quizbox.subscriptions.TagResponse;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public record RecommendationQuizPackResponse<T>(String filterType, Collection<T> data) {
    public static RecommendationQuizPackResponse<?> dummyOthers() {
        return new RecommendationQuizPackResponse<>(
                "OTHERS",
                Collections.of()
        );
    }

    public static RecommendationQuizPackResponse<?> dummyLearningDays() {

        return new RecommendationQuizPackResponse<>(
                "LEARNING_DAYS",
                Collections.of(
                        new LearningDaysRecommendationQuizPackResponse(
                                new QuizPackResponse(
                                        UUID.randomUUID().toString(),
                                        12,
                                        300,
                                        Collections.of(
                                                new TagResponse(UUID.randomUUID().toString(), "Backend"),
                                                new TagResponse(UUID.randomUUID().toString(), "Java")
                                        )
                                ),
                                LocalDateTime.now().minusDays(5)
                        ),
                        new LearningDaysRecommendationQuizPackResponse(
                                new QuizPackResponse(
                                        UUID.randomUUID().toString(),
                                        21,
                                        1003,
                                        Collections.of(
                                                new TagResponse(UUID.randomUUID().toString(), "모니터링"),
                                                new TagResponse(UUID.randomUUID().toString(), "CI/CD")
                                        )
                                ),
                                LocalDateTime.now().minusDays(12)
                        )
                )
        );
    }
}


record LearningDaysRecommendationQuizPackResponse(QuizPackResponse quizPackResponse, LocalDateTime localDateTime) {
}
