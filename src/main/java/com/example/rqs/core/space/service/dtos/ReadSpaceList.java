package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class ReadSpaceList {

    private final Member member;

    private final LocalDateTime lastAt;

    private final Long offset;

    private final String type;

    private ReadSpaceList(Member member, String lastAt, Long offset, String type) {
        this.member = member;
        this.lastAt = Objects.isNull(lastAt) ? null : LocalDateTime.parse(lastAt);
        this.offset = offset;
        this.type = type;
    }

    public static ReadSpaceList guest(Long offset) {
        return new ReadSpaceList(null, null, offset, "TRENDING");
    }

    public static ReadSpaceList guest(String lastAt) {
        return new ReadSpaceList(null, lastAt, null, "DEFAULT");
    }
}
