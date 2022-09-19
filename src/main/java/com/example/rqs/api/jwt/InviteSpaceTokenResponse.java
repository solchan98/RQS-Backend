package com.example.rqs.api.jwt;

import lombok.Getter;

@Getter
public class InviteSpaceTokenResponse {
    private final String inviteToken;

    private InviteSpaceTokenResponse(String inviteToken) {
        this.inviteToken = inviteToken;
    }

    public static InviteSpaceTokenResponse of (String inviteToken) {
        return new InviteSpaceTokenResponse(inviteToken);
    }
}
