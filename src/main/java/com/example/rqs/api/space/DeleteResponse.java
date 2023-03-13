package com.example.rqs.api.space;

import lombok.Getter;

@Getter
public class DeleteResponse {
    public final Long deletedId;
    private final boolean isDeleted;

    private DeleteResponse(Long deletedId, boolean isDeleted) {
        this.deletedId = deletedId;
        this.isDeleted = isDeleted;
    }

    public static DeleteResponse of(Long deletedId, boolean isDeleted) {
        return new DeleteResponse(deletedId, isDeleted);
    }
}
