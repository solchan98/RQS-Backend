package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class CreateSpace {

    private final Member creator;

    private final String title;

    private final boolean visibility;

    private CreateSpace(Member creator, String title, boolean visibility) {
        this.creator = creator;
        this.title = title;
        this.visibility = visibility;
    }

    public static CreateSpace of(Member creator, String title, boolean visibility) {
        return new CreateSpace(creator, title, visibility);
    }
}
