package com.example.rqs.api.space;

import lombok.Getter;

@Getter
public class JoinCodeDto {
    private final Long spaceId;
    private final String adminCode;
    private final String memberCode;

    public JoinCodeDto(Long spaceId, String adminCode, String memberCode) {
        this.spaceId = spaceId;
        this.adminCode = adminCode;
        this.memberCode = memberCode;
    }
}
