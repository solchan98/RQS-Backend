package com.example.rqs.core.spacemember;

public enum SpaceRole {
    ADMIN(999),
    MEMBER(998),
    GUEST(0);

    private final int seq;

    SpaceRole(int seq) {
        this.seq = seq;
    }

    public boolean goe(SpaceRole role) {
        return this.seq >= role.seq;
    }
}
