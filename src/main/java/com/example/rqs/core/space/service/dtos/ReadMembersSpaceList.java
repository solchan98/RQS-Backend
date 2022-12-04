package com.example.rqs.core.space.service.dtos;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class ReadMembersSpaceList {

    private final Long memberId;

    private final Long targetMemberId;

    private final LocalDateTime lastJoinedAt;

    private ReadMembersSpaceList (Long memberId, Long targetMemberId, String lastJoinedAt) {
        this.memberId = memberId;
        this.targetMemberId = targetMemberId;
        this.lastJoinedAt = Objects.isNull(lastJoinedAt) ? null : LocalDateTime.parse(lastJoinedAt);
    }

    public static ReadMembersSpaceList of(Long memberId, Long targetMemberId, String lastJoinedAt) {
        return new ReadMembersSpaceList(memberId, targetMemberId, lastJoinedAt);
    }

    public static ReadMembersSpaceList of(Long targetMemberId, String lastJoinedAt) {
        return new ReadMembersSpaceList(null, targetMemberId, lastJoinedAt);
    }
}
