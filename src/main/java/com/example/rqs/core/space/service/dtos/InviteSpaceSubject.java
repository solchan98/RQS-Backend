package com.example.rqs.core.space.service.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InviteSpaceSubject {

    private Long spaceId;

    private String spaceTitle;

    private Long inviterId;

    private String inviterNickname;

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
