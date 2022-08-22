package com.example.rqs.api.space;

import lombok.Getter;

@Getter
public class DeleteSpaceMemberResponse {

    public final Long spaceMemberId;

    private final boolean isDeleted;

    private DeleteSpaceMemberResponse(Long spaceMemberId, boolean isDeleted) {
        this.spaceMemberId = spaceMemberId;
        this.isDeleted = isDeleted;
    }

    public static DeleteSpaceMemberResponse of(Long spaceMemberId, boolean isDeleted) {
        return new DeleteSpaceMemberResponse(spaceMemberId, isDeleted);
    }
}
