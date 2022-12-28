package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.item.Item;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.space.service.dtos.SpaceMemberResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private Long itemId;

    private Long spaceId;

    private String question;

    private SpaceMemberResponse spaceMemberResponse;

    private String answer;

    private String hint;

    private LocalDateTime createdAt;

    public ItemResponse(Long itemId, Long spaceId, String question, SpaceMember spaceMember, String answer, String hint, LocalDateTime createdAt) {
        this.itemId = itemId;
        this.spaceId = spaceId;
        this.question = question;
        this.spaceMemberResponse = SpaceMemberResponse.of(spaceMember);
        this.answer = answer;
        this.hint = hint;
        this.createdAt = createdAt;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(
                item.getItemId(),
                item.getSpace().getSpaceId(),
                item.getQuestion(),
                item.getSpaceMember(),
                item.getAnswer(),
                item.getHint(),
                item.getCreatedAt()
        );
    }

}
