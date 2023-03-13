package com.example.rqs.core.space.service.dtos;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class ReadSpaces {

    private final LocalDateTime lastAt;

    private final Long offset;

    private final String type;

    private ReadSpaces(String lastAt, Long offset, String type) {
        this.lastAt = Objects.isNull(lastAt) ? null : LocalDateTime.parse(lastAt);
        this.offset = offset;
        this.type = type;
    }

    public static ReadSpaces offset(Long offset) {
        return new ReadSpaces(null, offset, "TRENDING");
    }

    public static ReadSpaces lastAt(String lastAt) {
        return new ReadSpaces(lastAt, null, "DEFAULT");
    }
}
