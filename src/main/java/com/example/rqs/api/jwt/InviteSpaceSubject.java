package com.example.rqs.api.jwt;

import lombok.Getter;

@Getter
public class InviteSpaceSubject {

    private final Long spaceId;

    private final String spaceTitle;

    private final Long inviterId;

    private final String inviterNickname;

    private InviteSpaceSubject(Long spaceId, String spaceTitle, Long inviterId, String inviterNickname) {
        this.spaceId = spaceId;
        this.spaceTitle = spaceTitle;
        this.inviterId = inviterId;
        this.inviterNickname = inviterNickname;
    }

    public static InviteSpaceSubject of(Long spaceId, String spaceTitle, Long inviterId, String inviterNickname) {
        return new InviteSpaceSubject(spaceId, spaceTitle, inviterId, inviterNickname);
    }
}
