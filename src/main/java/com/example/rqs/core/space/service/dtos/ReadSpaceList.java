package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class ReadSpaceList {

    private final Member member;

    private final LocalDateTime lastJoinedAt;

    private final Long offset;

    private final String type;

    private ReadSpaceList(Member member, String lastJoinedAt, Long offset, String type) {
        this.member = member;
        this.lastJoinedAt = Objects.isNull(lastJoinedAt) ? null : LocalDateTime.parse(lastJoinedAt);
        this.offset = offset;
        this.type = type;
    }

    public static ReadSpaceList auth(Member member, String lastJoinedAt) {
        return new ReadSpaceList(member, lastJoinedAt, null, "DEFAULT");
    }

    public static ReadSpaceList guest(Long offset) {
        return new ReadSpaceList(null, null, offset, "TRENDING");
    }

    public static ReadSpaceList guest(String lastJoinedAt) {
        return new ReadSpaceList(null, lastJoinedAt, null, "DEFAULT");
    }
}
