package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class CreateSpace {

    private final Member creator;

    private final String title;

    private final String content;

    private final String url;

    private final boolean visibility;

    private CreateSpace(Member creator, String title, String content, String url, boolean visibility) {
        this.creator = creator;
        this.title = title;
        this.content = content;
        this.url = url;
        this.visibility = visibility;
    }

    public static CreateSpace of(Member creator, String title, boolean visibility) {
        return new CreateSpace(creator, title, null, null, visibility);
    }

    public static CreateSpace of(Member creator, String title,  String content, boolean visibility) {
        return new CreateSpace(creator, title, content, null, visibility);
    }

    public static CreateSpace of(Member creator, String title,  String content, String url, boolean visibility) {
        return new CreateSpace(creator, title, content, url, visibility);
    }
}
