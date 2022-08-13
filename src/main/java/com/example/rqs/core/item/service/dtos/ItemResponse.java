package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.item.Item;
import lombok.Getter;

@Getter
public class ItemResponse {

    private final Long itemId;

    private final Long spaceId;

    private final String question;

    private final String answer;

    private final String hint;

    private ItemResponse(Long itemId, Long spaceId, String question, String answer, String hint) {
        this.itemId = itemId;
        this.spaceId = spaceId;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(
                item.getItemId(),
                item.getSpace().getSpaceId(),
                item.getQuestion(),
                item.getAnswer(),
                item.getHint()
        );
    }

}
