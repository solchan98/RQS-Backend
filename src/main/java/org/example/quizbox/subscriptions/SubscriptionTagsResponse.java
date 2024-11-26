package org.example.quizbox.subscriptions;

import io.jsonwebtoken.lang.Collections;

import java.util.Collection;
import java.util.UUID;

public record SubscriptionTagsResponse(
        String filterType,
        Collection<TagResponse> tags
) {

    public static SubscriptionTagsResponse dummyNewQuizzes() {
        return new SubscriptionTagsResponse("NEW_QUIZZES", Collections.of(
                new TagResponse(1, "모니터링"),
                new TagResponse(2, "Backend"),
                new TagResponse(3, "CI/CD"),
                new TagResponse(4, "ELK")
        ));
    }

    public static SubscriptionTagsResponse dummy() {
        return new SubscriptionTagsResponse("NEW_QUIZZES", Collections.of(
                new TagResponse(5, "모니터링"),
                new TagResponse(6, "Backend"),
                new TagResponse(7, "CI/CD"),
                new TagResponse(8, "ELK"),
                new TagResponse(9, "FrontEnd"),
                new TagResponse(10, "AL/ML"),
                new TagResponse(11, "Cloud")
        ));
    }
}
