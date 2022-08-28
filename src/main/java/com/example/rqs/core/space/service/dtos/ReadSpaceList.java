package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class ReadSpaceList {

    private final Member member;

    private final LocalDateTime lastJoinedAt;

    private final Boolean visibility;

    private ReadSpaceList(Member member, String lastJoinedAt, Boolean visibility) {
        this.member = member;
        this.lastJoinedAt = Objects.isNull(lastJoinedAt) ? null : LocalDateTime.parse(lastJoinedAt);
        this.visibility = visibility;
    }

    public static ReadSpaceList of(Member member, String lastJoinedAt, Boolean visibility) {
        return new ReadSpaceList(member, lastJoinedAt, visibility);
    }

    public static ReadSpaceList of(Member member, Boolean visibility) {
        return new ReadSpaceList(member, null, visibility);
    }
}
