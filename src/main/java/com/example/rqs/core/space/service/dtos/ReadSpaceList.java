package com.example.rqs.core.space.service.dtos;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class ReadSpaceList {

    private final LocalDateTime lastAt;

    private final Long offset;

    private final String type;

    private ReadSpaceList(String lastAt, Long offset, String type) {
        this.lastAt = Objects.isNull(lastAt) ? null : LocalDateTime.parse(lastAt);
        this.offset = offset;
        this.type = type;
    }

    public static ReadSpaceList offset(long offset) {
        return new ReadSpaceList(null, offset, "TRENDING");
    }

    public static ReadSpaceList lastAt(String lastAt) {
        return new ReadSpaceList(lastAt, null, "DEFAULT");
    }
}
