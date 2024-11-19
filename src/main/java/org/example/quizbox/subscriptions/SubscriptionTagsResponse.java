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
                new TagResponse(UUID.randomUUID().toString(), "모니터링"),
                new TagResponse(UUID.randomUUID().toString(), "Backend"),
                new TagResponse(UUID.randomUUID().toString(), "CI/CD"),
                new TagResponse(UUID.randomUUID().toString(), "ELK")
        ));
    }

    public static SubscriptionTagsResponse dummy() {
        return new SubscriptionTagsResponse("NEW_QUIZZES", Collections.of(
                new TagResponse(UUID.randomUUID().toString(), "모니터링"),
                new TagResponse(UUID.randomUUID().toString(), "Backend"),
                new TagResponse(UUID.randomUUID().toString(), "CI/CD"),
                new TagResponse(UUID.randomUUID().toString(), "ELK"),
                new TagResponse(UUID.randomUUID().toString(), "FrontEnd"),
                new TagResponse(UUID.randomUUID().toString(), "AL/ML"),
                new TagResponse(UUID.randomUUID().toString(), "Cloud")
        ));
    }
}
