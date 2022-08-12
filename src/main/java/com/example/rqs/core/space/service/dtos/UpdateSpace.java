package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class UpdateSpace {

    private final Long spaceId;

    private final Member member;

    private final String title;

    private UpdateSpace(Long spaceId, Member member, String title) {
        this.spaceId = spaceId;
        this.member = member;
        this.title = title;
    }

    public static UpdateSpace of(Long spaceId, Member member, String title) {
        return new UpdateSpace(spaceId, member, title);
    }
}
