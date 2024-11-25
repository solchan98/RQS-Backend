package org.example.quizbox.tag.presentation;

import org.example.quizbox.tag.application.GetOrCreateTagDto;

public record CreateTagRequest(
        String tag
) {

    public GetOrCreateTagDto toGetOrCreateTagDto() {
        return new GetOrCreateTagDto(tag);
    }
}
