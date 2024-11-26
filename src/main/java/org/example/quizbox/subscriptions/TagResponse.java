package org.example.quizbox.subscriptions;

import org.example.quizbox.tag.domain.Tag;

public record TagResponse(
        long id,
        String name
) {
    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());

    }
}
